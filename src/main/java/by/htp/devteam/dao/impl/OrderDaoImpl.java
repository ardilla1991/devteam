package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.OrderDao;
import by.htp.devteam.dao.util.ConnectionPool;

import static by.htp.devteam.dao.util.ConstantValue.*;

public final class OrderDaoImpl implements OrderDao {

	private static final int ID = 1;
	private static final int TITLE = 2;
	private static final int DESCRIPTION = 3;
	private static final int SPECIFICATION = 4;
	private static final int CUSTOMER_ID = 5;
	private static final int DATE_CREATED = 6;
	private static final int DATE_START = 7;
	private static final int DATE_FINISH = 8;
	private static final int DATE_PROCESSING = 9;
	private static final int PRICE = 10;

	private static final int ORDER_ID = 1;
	private static final int WORK_ID = 2;
	
	private static final int QUALIFICATION_ID = 2;
	private static final int COUNT = 3;
	
	public OrderDaoImpl() {
		super();
	}
	
	/*
	 * Order by dateStart DESC
	 * @see by.htp.devteam.dao.OrderDao#getNewOrders(int, int)
	 */
	@Override
	public PagingVo<Order> getNewOrders(int offset, int countPerPage) throws DaoException {
		PagingVo<Order> pagingVo = new PagingVo<>();
		try ( Connection dbConnection = ConnectionPool.getConnection();
			  PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_NEW_RECORDS_LIST);
			  Statement st = dbConnection.createStatement()) {
			
			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);
			pagingVo.setRecords(executeQueryAndGetOrderListFromResultSet(ps));
			try ( ResultSet rsNumebr  = st.executeQuery(SQL_FOUND_ROWS) ) {
				if ( rsNumebr.next() )
					pagingVo.setCountAllRecords(rsNumebr.getInt(1));
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ORDER_NEW_RECORDS_LIST, e);
		}

		return pagingVo;
	}
	
	private List<Order> executeQueryAndGetOrderListFromResultSet(PreparedStatement ps) throws SQLException {
		List<Order> orders = new ArrayList<>();
		try ( ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				Customer customer = createCustomerFromResultSet(rs);
				Order order = createOrderFromResultSet(rs, customer);
				orders.add(order);
			}
		}
		
		return orders;
	}
	
	private Customer createCustomerFromResultSet(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getLong(CUSTOMER_ID));
		customer.setName(rs.getString(12));
		customer.setEmail(rs.getString(13));
		customer.setPhone(rs.getString(14));
		return customer;
	}
	
	private Order createOrderFromResultSet(ResultSet rs, Customer customer) throws SQLException {
		Order order = new Order();
		order.setId(rs.getLong(ID));
		order.setTitle(rs.getString(TITLE));
		order.setDescription(rs.getString(DESCRIPTION));
		order.setSpecification(rs.getString(SPECIFICATION));
		order.setDateCreated(getDateFromTimestamp(rs.getTimestamp(DATE_CREATED)));
		order.setDateStart(rs.getDate(DATE_START));
		order.setDateFinish(rs.getDate(DATE_FINISH));
		order.setDateProcessing(getDateFromTimestamp(rs.getTimestamp(DATE_PROCESSING)));
		order.setPrice(rs.getBigDecimal(PRICE));
		order.setCustomer(customer);

		return order;
	}
	
	private Date getDateFromTimestamp(Timestamp timestamp) {
		Date date = null;
		if (timestamp != null)
		    date = new Date(timestamp.getTime());

		return date;
	}
	
	@Override
	public OrderVo getById(long id) throws DaoException, ObjectNotFoundException {
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
	
	private List<Work> getWorks(Connection dbConnection, Order order) throws SQLException{
		List<Work> works = new ArrayList<>();
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_GET_WORKS_BY_ORDER_ID) ) {
			
			ps.setLong(ORDER_ID, order.getId());
			works = executeQueryAndGetWorkListFromResultSet(ps);
		}
		
		return works;
	}
	
	private Map<Qualification, Integer> getQualifications(Connection dbConnection, Order order) throws SQLException {
		Map<Qualification, Integer> qualifications = new HashMap<>();	
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_GET_QUALIFICATIONS_BY_ORDER_ID) ) {

			ps.setLong(ORDER_ID, order.getId());
			qualifications = executeQueryAndGetQualificationsFromResultset(ps);
		}
		
		return qualifications;
	}
	
	private List<Work> executeQueryAndGetWorkListFromResultSet(PreparedStatement ps) throws SQLException {
		List<Work> works = new ArrayList<>();
		try ( ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				Work work = createWorkFromResultSet(rs);
				works.add(work);
			}
		}
		
		return works;
	}
	
	private Map<Qualification, Integer> executeQueryAndGetQualificationsFromResultset(PreparedStatement ps) 
			throws SQLException {
		Map<Qualification, Integer> qualifications = new HashMap<>();	
		try ( ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				Qualification qualification = createQualificationFromResultSet(rs);
				qualifications.put(qualification, rs.getInt(3));
			}
		}
		
		return qualifications;
	}
	
	private Work createWorkFromResultSet(ResultSet rs) throws SQLException {
		Work work = new Work();
		work.setId(rs.getLong(WORK_ID));
		work.setTitle(rs.getString(3));
		work.setDescription(rs.getString(4));
		
		return work;
	}
	
	private Qualification createQualificationFromResultSet(ResultSet rs) throws SQLException {
		Qualification qualification = new Qualification();
		qualification.setId(rs.getLong(1));
		qualification.setTitle(rs.getString(2));
		
		return qualification;
	}
	
	@Override
	public List<Order> getByCustomer(Customer customer) throws DaoException{
		List<Order> orders = new ArrayList<>();
		try ( Connection dbConnection = ConnectionPool.getConnection(); 
			  PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_GET_LIST_BY_CUSTOMER_ID) ) {

			ps.setLong(ID, customer.getId());
			orders = executeQueryAndGetOrderListFromResultSet(ps, customer);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ORDER_GET_LIST_BY_CUSTOMER_ID, e);
		}

		return orders;
	}
	
	private List<Order> executeQueryAndGetOrderListFromResultSet(PreparedStatement ps, Customer customer) 
			throws SQLException {
		List<Order> orders = new ArrayList<>();
		try ( ResultSet rs = ps.executeQuery() ) { 
			while ( rs.next() ) {
				Order order = createOrderFromResultSet(rs, customer);
				orders.add(order);
			}
		}
		
		return orders;
	}

	@Override
	public OrderVo add(OrderVo orderVo) throws DaoException{
		Connection dbConnection = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			dbConnection.setAutoCommit(false);
			Long id = addOrder(dbConnection, orderVo.getOrder());
			orderVo.getOrder().setId(id);
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
	
	private Long addOrder(Connection dbConnection, Order order) throws SQLException {
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
	
	/*
	 * Prepare batches for table-connection order-work
	 */
	private void addWorks(Connection dbConnection, Order order, List<Work> works) throws SQLException {
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_ADD_WORK) ) {

			prepareAndAddBatchesForWorks(ps, order, works);
			ps.executeBatch();
		}
	}
	
	/*
	 * Prepare batches for table-connection order-qualification
	 */
	private void addQualifications(Connection dbConnection, Order order, 
								   Map<Qualification, Integer> qualifications) throws SQLException {
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_ADD_QUALIFICATION) ) {

			prepareAndAddBatchesForQualifications(ps, order, qualifications);
			ps.executeBatch();
		}
	}
	
	private void prepareAndAddBatchesForWorks(PreparedStatement ps, Order order, 
											  List<Work> works) throws SQLException {
		for ( Work work : works ) {
			ps.setLong(ORDER_ID, order.getId());
			ps.setLong(WORK_ID, work.getId());
			
			ps.addBatch();
		}
	}
	
	private void prepareAndAddBatchesForQualifications(PreparedStatement ps, Order order, 
													   Map<Qualification, Integer> qualifications) 
			throws SQLException {
		for (Map.Entry<Qualification, Integer> entry : qualifications.entrySet()) {
		    ps.setLong(ORDER_ID, order.getId());
			ps.setLong(QUALIFICATION_ID, entry.getKey().getId());
			ps.setInt(COUNT, entry.getValue());
			
			ps.addBatch();
		}
	}
	
	@Override
	public void setPriceAndDateProcessing(Connection connection, Order order) throws DaoException {
		try ( PreparedStatement ps = connection.prepareStatement(SQL_ORDER_SET_PRICE) ) {
			ps.setBigDecimal(1, order.getPrice());
			ps.setTimestamp(2,  new java.sql.Timestamp(order.getDateProcessing().getTime()));
			ps.setLong(3, order.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ORDER_SET_PRICE, e);
		}	
	}
	
	private Order getOrder(Connection dbConnection, Long id) throws SQLException, ObjectNotFoundException {
		Order order = new Order();
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_GET_BY_ID) ) {

			ps.setLong(ID, id);
			order = executeQueryAndGetOrderFromResultSet(ps);
		}
		
		return order;
	}
	
	/*
	 * rollback query
	 */
	private void rollback(Connection dbConnection) throws DaoException {
		try {
			dbConnection.rollback();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ROLLBACK);
		}
	}
	
	private Order executeQueryAndGetOrderFromResultSet(PreparedStatement ps) throws SQLException, ObjectNotFoundException {
		Order order = new Order();
		try ( ResultSet rs = ps.executeQuery() ) {
			if ( rs.next() ) {
				Customer customer = createCustomerFromResultSet(rs);
				order = createOrderFromResultSet(rs, customer);
			} else {
				throw new ObjectNotFoundException(MSG_ORDER_NOT_FOUND);
			}
		}
		
		return order;
	}
	
	private void prepareStatementForOrder(PreparedStatement ps, Order order) throws SQLException {
		ps.setString(ID, null);
		ps.setString(TITLE, order.getTitle());
		ps.setString(DESCRIPTION, order.getDescription());
		ps.setString(SPECIFICATION, order.getSpecification());
		ps.setLong(CUSTOMER_ID, order.getCustomer().getId());
		ps.setTimestamp(DATE_CREATED, new java.sql.Timestamp(order.getDateCreated().getTime()));
		ps.setDate(DATE_START, new java.sql.Date(order.getDateStart().getTime()));
		ps.setDate(DATE_FINISH, new java.sql.Date(order.getDateFinish().getTime()));
		ps.setBigDecimal(9, order.getPrice());
	}


}
