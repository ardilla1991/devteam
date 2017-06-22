package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Work;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.OrderWorkDao;

public class OrderWorkDaoImpl implements OrderWorkDao{
	
	private final int ORDER_ID = 1;
	private final int WORK_ID = 2;
	private final int DESCRIPTION = 3;
	
	private final int WORK_TITLE = 5;
	private final int WORK_DESCRIPTION = 6;
	private final int WORK_PRICE = 7;
	
	private final String ADD = "INSERT INTO order_work (order_id, work_id) VALUES(?, ?)";
	private final String GET_BY_ORDER_ID = "SELECT ow.order_id, w.* FROM order_work as ow JOIN work as w ON w.id=ow.work_id "
			+ "WHERE ow.order_id=?";
	
	public void add(Order order, String[] ids) {
		System.out.println("ddddddddddddd");
		System.out.println(ids);
		System.out.println(ids.length);
		Connection dbConnection = null;
		PreparedStatement ps = null;
		
		try {
			dbConnection = ConnectionPool.getConnection();
			
			ps = dbConnection.prepareStatement(ADD);
			for ( String id : ids ) {
				System.out.println(id);
				ps.setLong(ORDER_ID, order.getId());
				ps.setLong(WORK_ID, Long.valueOf(id));
				
				ps.addBatch();
			}
			ps.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			ConnectionPool.close(dbConnection);
		}

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

	@Override
	public List<Work> getByOrder(Order order) {
		Connection dbConnection = null;
		PreparedStatement ps = null;
		List<Work> works = new ArrayList<Work>();
		
		try {
			dbConnection = ConnectionPool.getConnection();
			
			ps = dbConnection.prepareStatement(GET_BY_ORDER_ID);
			System.out.println("order=");
			System.out.println(order);
			ps.setLong(ORDER_ID, order.getId());
			
			ResultSet rs = ps.executeQuery();
			System.out.println("works");
			while ( rs.next() ) {
				Work work = new Work();
				work.setId(rs.getLong(WORK_ID));
				work.setTitle(rs.getString(2));
				work.setDescription(rs.getString(3));
				work.setPrice(rs.getInt(4));
				
				works.add(work);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			ConnectionPool.close(dbConnection);
		}
		return works;
	}
	
}
