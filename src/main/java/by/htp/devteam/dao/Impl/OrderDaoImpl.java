package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.bean.dto.OrderListDto;
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.OrderDao;

public class OrderDaoImpl implements OrderDao{

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
	private final int CUSTOMER_NAME = 12;
	private final int CUSTOMER_EMAIL = 15;
	private final int CUSTOMER_PHONE = 16;
	
	private final String NEW_RECORDS_LIST = "SELECT SQL_CALC_FOUND_ROWS o.*, c.* "
			+ "FROM `order` as o "
			+ "JOIN customer as c "
			+ "ON o.customer_id=c.id "
			+ "WHERE o.status=0 ORDER BY o.dateCreated LIMIT ?,?";
	private final String GET_BY_ID = "SELECT o.*, c.* FROM `order` as o JOIN customer as c ON o.customer_id=c.id "
			+ " WHERE o.id=?";
	
	private final String GET_ORDERS_BY_CUSTOMER_ID = "SELECT * FROM `order` WHERE customer_id=?";
	
	private final String ADD = "INSERT INTO `order` (id, title, description, specification, customer_id, status, dateCreated, dateStart, dateFinish) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public OrderListDto getNewOrders(int offset, int countPerPage) {
		OrderListDto orderDto = new OrderListDto();
		List<Order> projects = new ArrayList<Order>();
		int numberOfRecords = 0;
		
		Connection dbConnection = null;
		PreparedStatement ps = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			
			ps = dbConnection.prepareStatement(NEW_RECORDS_LIST);
			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
				System.out.println(rs);
				Order order = new Order();
				order.setId(rs.getLong(ID));
				order.setTitle(rs.getString(TITLE));
				order.setDescription(rs.getString(DESCRIPTION));
				order.setSpecification(rs.getString(SPECIFICATION));
				order.setStatus(rs.getBoolean(STATUS));
				order.setDateCreated(rs.getDate(DATE_CREATED));
				order.setDateStart(rs.getDate(DATE_START));
				order.setDateFinish(rs.getDate(DATE_FINISH));
				
				Customer customer = new Customer();
				customer.setId(rs.getLong(CUSTOMER_ID));
				customer.setName(rs.getString(CUSTOMER_NAME));
				customer.setEmail(rs.getString(CUSTOMER_EMAIL));
				customer.setPhone(rs.getString(CUSTOMER_PHONE));
				order.setCustomer(customer);
				
				projects.add(order);
			}
			rs.close();
			
			Statement st = dbConnection.createStatement();
			ResultSet rsNumebr  = st.executeQuery("SELECT FOUND_ROWS()");
			if ( rsNumebr.next() )
				numberOfRecords = rsNumebr.getInt(1);
			
			orderDto.setOrders(projects);
			orderDto.setCountRecords(numberOfRecords);
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			ConnectionPool.close(dbConnection);
		}
		return orderDto;
	}
	
	public Order getById(long id) {
		
		Connection dbConnection = null;
		PreparedStatement ps = null;
		Order order = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			
			ps = dbConnection.prepareStatement(GET_BY_ID);
			System.out.println(id);
			ps.setLong(ID, id);
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				order = new Order();
				order.setId(rs.getLong(ID));
				order.setTitle(rs.getString(TITLE));
				order.setDescription(rs.getString(DESCRIPTION));
				order.setSpecification(rs.getString(SPECIFICATION));
				order.setStatus(rs.getBoolean(STATUS));
				order.setDateCreated(rs.getDate(DATE_CREATED));
				order.setDateStart(rs.getDate(DATE_START));
				order.setDateFinish(rs.getDate(DATE_FINISH));
				
				Customer customer = new Customer();
				customer.setId(rs.getLong(CUSTOMER_ID));
				customer.setName(rs.getString(CUSTOMER_NAME));
				customer.setEmail(rs.getString(CUSTOMER_EMAIL));
				customer.setPhone(rs.getString(CUSTOMER_PHONE));
				order.setCustomer(customer);
				System.out.println("ORDER ORDER ORDER=");
				System.out.println(order);
			}
			System.out.println("ORDER2 ORDER ORDER=");
			System.out.println(order);
			rs.close();			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			ConnectionPool.close(dbConnection);
		}
		return order;
	}
	
	/*public long add(Order order) {
		
	}*/
	
	private void close(PreparedStatement ps) {
		if ( ps != null ) {
			try {
				ps.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Order> geOrdersByCustomer(Customer customer) {

		List<Order> orders = new ArrayList<Order>();

		Connection dbConnection = null;
		PreparedStatement ps = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			
			ps = dbConnection.prepareStatement(GET_ORDERS_BY_CUSTOMER_ID);
			ps.setLong(ID, customer.getId());
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
				System.out.println(rs);
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
				
				orders.add(order);
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			ConnectionPool.close(dbConnection);
		}

		return orders;
	}

	@Override
	public Order add(Order order) {
		Connection dbConnection = null;
		PreparedStatement ps = null;
		
		Long id = (long) 0;
		try {
			dbConnection = ConnectionPool.getConnection();
			
			ps = dbConnection.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(ID, null);
			ps.setString(TITLE, order.getTitle());
			ps.setString(DESCRIPTION, order.getDescription());
			ps.setString(SPECIFICATION, order.getSpecification());
			ps.setLong(CUSTOMER_ID, order.getCustomer().getId());
			ps.setBoolean(STATUS, order.isStatus());
			ps.setDate(DATE_CREATED, order.getDateCreated());
			ps.setDate(DATE_START, order.getDateStart());
			ps.setDate(DATE_FINISH, order.getDateFinish());
			
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
			    id = rs.getLong(ID);
			    order.setId(id);
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			ConnectionPool.close(dbConnection);
		}
		return order;
	}

}
