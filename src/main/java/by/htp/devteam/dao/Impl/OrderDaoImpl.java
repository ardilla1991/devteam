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
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.OrderDao;
import by.htp.devteam.dao.SqlStatementConstantValue;

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
	private final int CUSTOMER_NAME = 11;
	private final int CUSTOMER_EMAIL = 12;
	private final int CUSTOMER_PHONE = 13;
	
	public OrderDto getNewOrders(int offset, int countPerPage) {
		OrderDto orderDto = new OrderDto();
		List<Order> projects = new ArrayList<Order>();
		int numberOfRecords = 0;
		
		Connection dbConnection = null;
		PreparedStatement ps = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			
			ps = dbConnection.prepareStatement(SqlStatementConstantValue.ORDER_NEW_LIST);
			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
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
	
	private void close(PreparedStatement ps) {
		if ( ps != null ) {
			try {
				ps.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
}
