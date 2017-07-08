package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.bean.dto.OrderVo;
import by.htp.devteam.bean.dto.OrderListVo;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.OrderDao;
import by.htp.devteam.dao.util.ConnectionPool;

import static by.htp.devteam.dao.util.ConstantValue.*;

public class OrderDaoImpl implements OrderDao{

	private final static int ID = 1;
	private final static int TITLE = 2;
	private final static int DESCRIPTION = 3;
	private final static int SPECIFICATION = 4;
	private final static int CUSTOMER_ID = 5;
	private final static int STATUS = 6;
	private final static int DATE_CREATED = 7;
	private final static int DATE_START = 8;
	private final static int DATE_FINISH = 9;
	private final static int PRICE = 10;

	private final static int ORDER_ID = 1;
	private final static int WORK_ID = 2;
	
	private final static int QUALIFICATION_ID = 2;
	private final static int COUNT = 3;
	
	

	
	@Override
	public OrderListVo getNewOrders(int offset, int countPerPage) throws DaoException {
		OrderListVo orderListVo = new OrderListVo();
		try ( Connection dbConnection = ConnectionPool.getConnection();
			  PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_NEW_RECORDS_LIST);
			  Statement st = dbConnection.createStatement()) {
			
			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);
			orderListVo.setOrders(getOrderListFromResultSet(ps));
			try ( ResultSet rsNumebr  = st.executeQuery(SQL_FOUND_ROWS) ) {
				if ( rsNumebr.next() )
					orderListVo.setCountRecords(rsNumebr.getInt(1));
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ORDER_NEW_RECORDS_LIST, e);
		}

		return orderListVo;
	}
	
	@Override
	public List<Order> getByCustomer(Customer customer) throws DaoException{
		List<Order> orders = new ArrayList<Order>();
		try ( Connection dbConnection = ConnectionPool.getConnection(); 
			  PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_GET_LIST_BY_CUSTOMER_ID) ) {

			ps.setLong(ID, customer.getId());
			orders = getOrderListFromResultSet(ps, customer);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ORDER_GET_LIST_BY_CUSTOMER_ID, e);
		}

		return orders;
	}

	@Override
	public OrderVo add(OrderVo orderVo) throws DaoException{	
		Connection dbConnection = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			dbConnection.setAutoCommit(false);
			orderVo.getOrder().setId(addOrder(dbConnection, orderVo.getOrder()));
			addWorks(dbConnection, orderVo.getOrder(), orderVo.getWorks());
			addQualifications(dbConnection, orderVo.getOrder(), orderVo.getQualifications());
			dbConnection.commit();
		} catch (SQLException e) {
			rollback(dbConnection);
			ConnectionPool.returnConnection(dbConnection);
			throw new DaoException(MSG_ERROR_ORDER_ADD, e);
		}
		return orderVo;
	}
	
	@Override
	public OrderVo getById(long id) throws DaoException {
		OrderVo orderVo = new OrderVo();
		try ( Connection dbConnection = ConnectionPool.getConnection()) {
			Order order = getOrder(dbConnection, id);
			orderVo.setOrder(order);
			orderVo.setWorks(getWorks(dbConnection, order));
			orderVo.setQualifications(getQualifications(dbConnection, order));
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ORDER_GET_BY_ID, e);
		}
		return orderVo;
	}
	
	@Override
	public void setPrice(Connection connection, Order order) throws DaoException {
		try ( PreparedStatement ps = connection.prepareStatement(SQL_ORDER_SET_PRICE) ) {
			ps.setBigDecimal(1, order.getPrice());
			ps.setLong(2, order.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ORDER_SET_PRICE, e);
		}
		
	}
	
	private Order getOrder(Connection dbConnection, Long id) throws SQLException {
		Order order = new Order();
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_GET_BY_ID) ) {

			ps.setLong(ID, id);
			order = getOrderFromResultSet(ps);
		}
		
		return order;
	}
	
	private List<Work> getWorks(Connection dbConnection, Order order) throws SQLException{
		List<Work> works = new ArrayList<Work>();
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_GET_WORKS_BY_ORDER_ID) ) {
			
			ps.setLong(ORDER_ID, order.getId());
			works = getWorkListFromResultSet(ps);
		}
		
		return works;
	}
	
	private Map<Qualification, Integer> getQualifications(Connection dbConnection, Order order) throws SQLException {
		Map<Qualification, Integer> qualifications = new HashMap<Qualification, Integer>();	
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_GET_QUALIFICATIONS_BY_ORDER_ID) ) {

			ps.setLong(ORDER_ID, order.getId());
			qualifications = getQualificationsFromResultset(ps);
		}
		
		return qualifications;
	}
	
	private Long addOrder(Connection dbConnection, Order order) throws SQLException{
		Long id = 0L;
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_ADD, PreparedStatement.RETURN_GENERATED_KEYS) ) {
			prepareStatementForOrder(ps, order);
			ps.executeUpdate();
			try ( ResultSet rs = ps.getGeneratedKeys() ) {
				if (rs.next()) {
				    id  = rs.getLong(ID);
				}
			}
		}
		
		return id;
	}
	
	public void addWorks(Connection dbConnection, Order order, List<Work> works) throws SQLException {
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_ADD_WORK) ) {

			prepareAndAddBatchesForWorks(ps, order, works);
			
			ps.executeBatch();
		}
	}
	
	public void addQualifications(Connection dbConnection, Order order, Map<Qualification, Integer> qualifications) throws SQLException {
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_ADD_QUALIFICATION) ) {

			prepareAndAddBatchesForQualifications(ps, order, qualifications);
			ps.executeBatch();
		}
	}
	
	private void rollback(Connection dbConnection) throws DaoException{
		try {
			dbConnection.rollback();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ROLLBACK);
		}
	}
	
	private Order createOrderFromResultSet(ResultSet rs, Customer customer) throws SQLException {
		Order order = new Order();
		order.setId(rs.getLong(ID));
		order.setTitle(rs.getString(TITLE));
		order.setDescription(rs.getString(DESCRIPTION));
		order.setSpecification(rs.getString(SPECIFICATION));
		order.setStatus(rs.getBoolean(STATUS));
		order.setDateCreated(rs.getDate(DATE_CREATED));
		order.setDateStart(rs.getDate(DATE_START));
		order.setDateFinish(rs.getDate(DATE_FINISH));
		order.setPrice(rs.getBigDecimal(PRICE));
		order.setCustomer(customer);
		
		return order;
	}
	
	private Work createWorkFromResultSet(ResultSet rs) throws SQLException {
		Work work = new Work();
		work.setId(rs.getLong(WORK_ID));
		work.setTitle(rs.getString(3));
		work.setDescription(rs.getString(4));
		work.setPrice(rs.getInt(5));
		
		return work;
	}
	
	private Qualification createQualificationFromResultSet(ResultSet rs) throws SQLException {
		Qualification qualification = new Qualification();
		qualification.setId(rs.getLong(1));
		qualification.setTitle(rs.getString(2));
		
		return qualification;
	}
	
	private Customer createCustomerFromResultSet(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getLong(CUSTOMER_ID));
		customer.setName(rs.getString(12));
		customer.setEmail(rs.getString(13));
		customer.setPhone(rs.getString(14));
		return customer;
	}
	
	private List<Order> getOrderListFromResultSet(PreparedStatement ps) throws SQLException {
		List<Order> orders = new ArrayList<Order>();
		try ( ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				Customer customer = createCustomerFromResultSet(rs);
				Order order = createOrderFromResultSet(rs, customer);
				orders.add(order);
			}
		}
		
		return orders;
	}
	
	private List<Order> getOrderListFromResultSet(PreparedStatement ps, Customer customer) throws SQLException {
		List<Order> orders = new ArrayList<Order>();
		try ( ResultSet rs = ps.executeQuery() ) { 
			while ( rs.next() ) {
				Order order = createOrderFromResultSet(rs, customer);
				orders.add(order);
			}
		}
		
		return orders;
	}
	
	private Order getOrderFromResultSet(PreparedStatement ps) throws SQLException {
		Order order = new Order();
		try ( ResultSet rs = ps.executeQuery() ) {
			if ( rs.next() ) {
				Customer customer = createCustomerFromResultSet(rs);
				order = createOrderFromResultSet(rs, customer);
			}
		}
		
		return order;
	}
	
	private List<Work> getWorkListFromResultSet(PreparedStatement ps) throws SQLException {
		List<Work> works = new ArrayList<Work>();
		try ( ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				Work work = createWorkFromResultSet(rs);
				works.add(work);
			}
		}
		
		return works;
	}
	
	private Map<Qualification, Integer> getQualificationsFromResultset(PreparedStatement ps) throws SQLException {
		Map<Qualification, Integer> qualifications = new HashMap<Qualification, Integer>();	
		try ( ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				Qualification qualification = createQualificationFromResultSet(rs);
				qualifications.put(qualification, rs.getInt(3));
			}
		}
		
		return qualifications;
	}
	
	private void prepareStatementForOrder(PreparedStatement ps, Order order) throws SQLException{
		ps.setString(ID, null);
		ps.setString(TITLE, order.getTitle());
		ps.setString(DESCRIPTION, order.getDescription());
		ps.setString(SPECIFICATION, order.getSpecification());
		ps.setLong(CUSTOMER_ID, order.getCustomer().getId());
		ps.setBoolean(STATUS, order.isStatus());
		ps.setDate(DATE_CREATED, order.getDateCreated());
		ps.setDate(DATE_START, order.getDateStart());
		ps.setDate(DATE_FINISH, order.getDateFinish());
		ps.setBigDecimal(PRICE, order.getPrice());
	}
	
	private void prepareAndAddBatchesForWorks(PreparedStatement ps, Order order, List<Work> works) throws SQLException {
		for ( Work work : works ) {
			ps.setLong(ORDER_ID, order.getId());
			ps.setLong(WORK_ID, work.getId());
			
			ps.addBatch();
		}
	}
	
	private void prepareAndAddBatchesForQualifications(PreparedStatement ps, Order order, Map<Qualification, Integer> qualifications) throws SQLException {
		for (Map.Entry<Qualification, Integer> entry : qualifications.entrySet()) {
		    ps.setLong(ORDER_ID, order.getId());
			ps.setLong(QUALIFICATION_ID, entry.getKey().getId());
			ps.setInt(COUNT, entry.getValue());
			
			ps.addBatch();
		}
	}


}
