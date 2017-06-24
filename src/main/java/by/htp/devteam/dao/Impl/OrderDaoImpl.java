package by.htp.devteam.dao.Impl;

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
import by.htp.devteam.bean.dto.OrderListDto;
import by.htp.devteam.controller.ConnectionPool;
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
	
	private final String NEW_RECORDS_LIST = "SELECT SQL_CALC_FOUND_ROWS o.*, c.* "
			+ "FROM `order` as o "
			+ "JOIN customer as c "
			+ "ON o.customer_id=c.id "
			+ "WHERE o.status=0 ORDER BY o.dateCreated LIMIT ?,?";
	private final String GET_BY_ID = "SELECT o.*, c.* FROM `order` as o "
			+ " JOIN customer as c ON o.customer_id=c.id "
			+ " WHERE o.id=?";
	
	private final String GET_ORDERS_BY_CUSTOMER_ID = "SELECT * FROM `order` "
			+ "WHERE customer_id=?";
	
	private final String ADD = "INSERT INTO `order` (id, title, description, specification, customer_id, status, dateCreated, dateStart, dateFinish) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private final String ADD_WORK = "INSERT INTO order_work (order_id, work_id) VALUES(?, ?)";
	
	private final String GET_WORKS_BY_ORDER_ID = "SELECT ow.order_id, w.* "
			+ "FROM order_work as ow JOIN work as w ON w.id = ow.work_id "
			+ "WHERE ow.order_id=?";
	
	private final String ADD_QUALIFICATION = "INSERT INTO order_qualification (order_id, qualification_id, count) "
			+ "VALUES(?, ?, ?)";
	
	private final String GET_QUALIFICATIONS_BY_ORDER_ID = "SELECT q.*, oq.count "
			+ "FROM qualification as q JOIN order_qualification as oq ON q.id = oq.qualification_id "
			+ "WHERE oq.order_id=?";
	
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
	
	public Order getOrder(long id) {
		Order order = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
			  PreparedStatement ps = dbConnection.prepareStatement(GET_BY_ID)	) {

			ps.setLong(ID, id);
			try ( ResultSet rs = ps.executeQuery() ) {
				order = getOrderFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public List<Order> geOrdersByCustomer(Customer customer) {
		List<Order> orders = new ArrayList<Order>();
		try ( Connection dbConnection = ConnectionPool.getConnection(); 
			  PreparedStatement ps = dbConnection.prepareStatement(GET_ORDERS_BY_CUSTOMER_ID) ) {

			ps.setLong(ID, customer.getId());
			try ( ResultSet rs = ps.executeQuery() ) { 
				orders = getOrderListFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orders;
	}

	@Override
	public Order addOrder(Order order) {	
		try ( Connection dbConnection = ConnectionPool.getConnection(); 
				PreparedStatement ps = dbConnection.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS) ) {

			prepareStatementForOrder(ps, order);
			ps.executeUpdate();
			try ( ResultSet rs = ps.getGeneratedKeys() ) {
				if (rs.next()) {
				    order.setId(rs.getLong(ID));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}
	
	public void addWorks(Order order, String[] ids) {
		try ( Connection dbConnection =  ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(ADD_WORK) ) {

			prepareAndAddBatchesForWorks(ps, order, ids);
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Work> getWorks(Order order) {
		List<Work> works = new ArrayList<Work>();
		try ( Connection dbConnection = ConnectionPool.getConnection(); 
				PreparedStatement ps = dbConnection.prepareStatement(GET_WORKS_BY_ORDER_ID) ) {
			
			ps.setLong(ORDER_ID, order.getId());
			try ( ResultSet rs = ps.executeQuery() ) {
				works = getWorkListFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return works;
	}
	
	@Override
	public void addQualifications(Order order, HashMap<Qualification, Integer> qualifications) {
		try ( Connection dbConnection = ConnectionPool.getConnection();
			  PreparedStatement ps = dbConnection.prepareStatement(ADD_QUALIFICATION) ) {

			prepareAndAddBatchesForQualifications(ps, order, qualifications);
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Map<Qualification, Integer> getQualifications(Order order) {
		Map<Qualification, Integer> qualifications = new HashMap<Qualification, Integer>();	
		try ( Connection dbConnection = ConnectionPool.getConnection();
			  PreparedStatement ps = dbConnection.prepareStatement(GET_QUALIFICATIONS_BY_ORDER_ID) ) {

			ps.setLong(ORDER_ID, order.getId());
			try ( ResultSet rs = ps.executeQuery() ) {
				qualifications = getQualificationsFromResultset(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qualifications;
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
		work.setTitle(rs.getString(2));
		work.setDescription(rs.getString(3));
		work.setPrice(rs.getInt(4));
		
		return work;
	}
	
	private Qualification createQualificationFromResultSet(ResultSet rs) throws SQLException {
		Qualification qualification = new Qualification();
		qualification.setId(rs.getLong(1));
		qualification.setTitle(rs.getString(2));
		
		return qualification;
	}
	
	private Customer createCustomerFtomResultSet(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getLong(CUSTOMER_ID));
		customer.setName(rs.getString(12));
		customer.setEmail(rs.getString(15));
		customer.setPhone(rs.getString(16));
		
		return customer;
	}
	
	private List<Order> getOrderListFromResultSet(ResultSet rs) throws SQLException {
		List<Order> orders = new ArrayList<Order>();
		while ( rs.next() ) {
			Customer customer = createCustomerFtomResultSet(rs);
			Order order = createOrderFromResultSet(rs, customer);
			orders.add(order);
		}
		
		return orders;
	}
	
	private Order getOrderFromResultSet(ResultSet rs) throws SQLException {
		Order order = new Order();
		if ( rs.next() ) {
			Customer customer = createCustomerFtomResultSet(rs);
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
	}
	
	private void prepareAndAddBatchesForWorks(PreparedStatement ps, Order order, String[] ids) throws SQLException {
		for ( String id : ids ) {
			ps.setLong(ORDER_ID, order.getId());
			ps.setLong(WORK_ID, Long.valueOf(id));
			
			ps.addBatch();
		}
	}
	
	private void prepareAndAddBatchesForQualifications(PreparedStatement ps, Order order, HashMap<Qualification, Integer> qualifications) throws SQLException {
		for (Map.Entry<Qualification, Integer> entry : qualifications.entrySet()) {
		    ps.setLong(ORDER_ID, order.getId());
			ps.setLong(QUALIFICATION_ID, entry.getKey().getId());
			ps.setInt(COUNT, entry.getValue());
			
			ps.addBatch();
		}
	}
}
