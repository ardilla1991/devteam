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
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.bean.dto.OrderListDto;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.OrderDao;

public class OrderDaoImpl extends CommonDao implements OrderDao{

	private final int ID = 1;
	private final int TITLE = 2;
	private final int DESCRIPTION = 3;
	private final int SPECIFICATION = 4;
	private final int CUSTOMER_ID = 5;
	private final int STATUS = 6;
	private final int DATE_CREATED = 7;
	private final int DATE_START = 8;
	private final int DATE_FINISH = 9;
	private final int PRICE = 10;

	private final int ORDER_ID = 1;
	private final int WORK_ID = 2;
	
	private final int QUALIFICATION_ID = 2;
	private final int COUNT = 3;
	
	private final static String NEW_RECORDS_LIST = "SELECT SQL_CALC_FOUND_ROWS o.*, c.* "
			+ "FROM `order` as o "
			+ "JOIN customer as c "
			+ "ON o.customer_id=c.id "
			+ "WHERE o.price=0 ORDER BY o.dateCreated, o.dateStart LIMIT ?,?";
	private final static String GET_BY_ID = "SELECT o.*, c.* FROM `order` as o "
			+ " JOIN customer as c ON o.customer_id=c.id "
			+ " WHERE o.id=?";
	
	private final static String GET_ORDERS_BY_CUSTOMER_ID = "SELECT * FROM `order` "
			+ "WHERE customer_id=?";
	
	private final static String ADD = "INSERT INTO `order` (id, title, description, specification, customer_id, status, dateCreated, dateStart, dateFinish, price) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private final static String ADD_WORK = "INSERT INTO order_work (order_id, work_id) VALUES(?, ?)";
	
	private final static String GET_WORKS_BY_ORDER_ID = "SELECT ow.order_id, w.* "
			+ "FROM order_work as ow JOIN work as w ON w.id = ow.work_id "
			+ "WHERE ow.order_id=?";
	
	private final static String ADD_QUALIFICATION = "INSERT INTO order_qualification (order_id, qualification_id, count) "
			+ "VALUES(?, ?, ?)";
	
	private final static String GET_QUALIFICATIONS_BY_ORDER_ID = "SELECT q.*, oq.count "
			+ "FROM qualification as q JOIN order_qualification as oq ON q.id = oq.qualification_id "
			+ "WHERE oq.order_id=?";
	
	private final static String SET_PRICE = "UPDATE `order` SET price=? WHERE id=?";
	
	public OrderListDto getNewOrders(int offset, int countPerPage) {
		OrderListDto orderDto = new OrderListDto();
		try ( Connection dbConnection = ConnectionPool.getConnection();
			  PreparedStatement ps = dbConnection.prepareStatement(NEW_RECORDS_LIST);
			  Statement st = dbConnection.createStatement()) {
			
			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);
			try ( ResultSet rs = ps.executeQuery() ) {
				orderDto.setOrders(getOrderListFromResultSet(rs));
			}
			try ( ResultSet rsNumebr  = st.executeQuery("SELECT FOUND_ROWS()") ) {
				if ( rsNumebr.next() )
					orderDto.setCountRecords(rsNumebr.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderDto;
	}
	
	public OrderDto getById(long id) {
		OrderDto orderDto = new OrderDto();
		Connection dbConnection = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			Order order = getOrder(dbConnection, id);
			orderDto.setOrder(order);
			orderDto.setWorks(getWorks(dbConnection, order));
			orderDto.setQualifications(getQualifications(dbConnection, order));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderDto;
	}
	
	private Order getOrder(Connection dbConnection, Long id) throws SQLException {
		Order order = null;
		try ( PreparedStatement ps = dbConnection.prepareStatement(GET_BY_ID) ) {

			ps.setLong(ID, id);
			try ( ResultSet rs = ps.executeQuery() ) {
				order = getOrderFromResultSet(rs);
			}
		}
		
		return order;
	}
	
	private List<Work> getWorks(Connection dbConnection, Order order) throws SQLException{
		List<Work> works = new ArrayList<Work>();
		try ( PreparedStatement ps = dbConnection.prepareStatement(GET_WORKS_BY_ORDER_ID) ) {
			ps.setLong(ORDER_ID, order.getId());
			try ( ResultSet rs = ps.executeQuery() ) {
				works = getWorkListFromResultSet(rs);
			}
		}
		return works;
	}
	
	private Map<Qualification, Integer> getQualifications(Connection dbConnection, Order order) throws SQLException {
		Map<Qualification, Integer> qualifications = new HashMap<Qualification, Integer>();	
		try ( PreparedStatement ps = dbConnection.prepareStatement(GET_QUALIFICATIONS_BY_ORDER_ID) ) {

			ps.setLong(ORDER_ID, order.getId());
			try ( ResultSet rs = ps.executeQuery() ) {
				qualifications = getQualificationsFromResultset(rs);
			}
		}
		
		return qualifications;
	}

	@Override
	public List<Order> getByCustomer(Customer customer) {
		List<Order> orders = new ArrayList<Order>();
		try ( Connection dbConnection = ConnectionPool.getConnection(); 
			  PreparedStatement ps = dbConnection.prepareStatement(GET_ORDERS_BY_CUSTOMER_ID) ) {

			ps.setLong(ID, customer.getId());
			try ( ResultSet rs = ps.executeQuery() ) { 
				orders = getOrderListFromResultSet(rs, customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orders;
	}

	@Override
	public OrderDto add(OrderDto orderDto) throws DaoException{	
		Connection dbConnection = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			dbConnection.setAutoCommit(false);
			orderDto.getOrder().setId(addOrder(dbConnection, orderDto.getOrder()));
			addWorks(dbConnection, orderDto.getOrder(), orderDto.getWorks());
			addQualifications(dbConnection, orderDto.getOrder(), orderDto.getQualifications());
			dbConnection.commit();
			
		} catch (SQLException e) {
			rollback(dbConnection);
			ConnectionPool.returnConnection(dbConnection);
			e.printStackTrace();
		}
		return orderDto;
	}
	
	private Long addOrder(Connection dbConnection, Order order) throws SQLException{
		Long id = 0L;
		try ( PreparedStatement ps = dbConnection.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS) ) {
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
		try ( PreparedStatement ps = dbConnection.prepareStatement(ADD_WORK) ) {

			prepareAndAddBatchesForWorks(ps, order, works);
			ps.executeBatch();
		}
	}
	
	public void addQualifications(Connection dbConnection, Order order, Map<Qualification, Integer> qualifications) throws SQLException {
		try ( PreparedStatement ps = dbConnection.prepareStatement(ADD_QUALIFICATION) ) {

			prepareAndAddBatchesForQualifications(ps, order, qualifications);
			ps.executeBatch();
		}
	}
	
	private void rollback(Connection dbConnection) throws DaoException{
		try {
			dbConnection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("couldn't rollback");
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
	
	private List<Order> getOrderListFromResultSet(ResultSet rs) throws SQLException {
		List<Order> orders = new ArrayList<Order>();
		while ( rs.next() ) {
			Customer customer = createCustomerFromResultSet(rs);
			Order order = createOrderFromResultSet(rs, customer);
			orders.add(order);
		}
		
		return orders;
	}
	
	private List<Order> getOrderListFromResultSet(ResultSet rs, Customer customer) throws SQLException {
		List<Order> orders = new ArrayList<Order>();
		while ( rs.next() ) {
			Order order = createOrderFromResultSet(rs, customer);
			orders.add(order);
		}
		
		return orders;
	}
	
	private Order getOrderFromResultSet(ResultSet rs) throws SQLException {
		Order order = new Order();
		if ( rs.next() ) {
			Customer customer = createCustomerFromResultSet(rs);
			order = createOrderFromResultSet(rs, customer);
		}
		
		return order;
	}
	
	private List<Work> getWorkListFromResultSet(ResultSet rs) throws SQLException {
		List<Work> works = new ArrayList<Work>();
		while ( rs.next() ) {
			Work work = createWorkFromResultSet(rs);
			works.add(work);
		}
		
		return works;
	}
	
	private Map<Qualification, Integer> getQualificationsFromResultset(ResultSet rs) throws SQLException {
		Map<Qualification, Integer> qualifications = new HashMap<Qualification, Integer>();	
		while ( rs.next() ) {
			Qualification qualification = createQualificationFromResultSet(rs);
			qualifications.put(qualification, rs.getInt(3));
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

	@Override
	public void setPrice(Connection connection, Order order) {
		try ( PreparedStatement ps = connection.prepareStatement(SET_PRICE) ) {
			ps.setBigDecimal(1, order.getPrice());
			ps.setLong(2, order.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
