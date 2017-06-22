package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.OrderQualificationDao;

public class OrderQualificationDaoImpl implements OrderQualificationDao{

	private final int ORDER_ID = 1;
	private final int QUALIFICATION_ID = 2;
	private final int COUNT = 3;
	private final int QUALIFICATION_TITLE = 4;
	
	private final String ADD = "INSERT INTO order_qualification (order_id, qualification_id, count) VALUES(?, ?, ?)";
	
	private final String GET_BY_ORDER_ID = "SELECT q.*, oq.count FROM qualification as q JOIN order_qualification as oq ON q.id=oq.qualification_id "
			+ "WHERE oq.order_id=?";
	
	public OrderQualificationDaoImpl() {
		super();
	}

	@Override
	public void add(Order order, HashMap<Qualification, Integer> qualifications) {
		Connection dbConnection = null;
		PreparedStatement ps = null;
		
		try {
			dbConnection = ConnectionPool.getConnection();
			
			ps = dbConnection.prepareStatement(ADD);
			
			for (Map.Entry<Qualification, Integer> entry : qualifications.entrySet()) {
			    Qualification qualification = entry.getKey();
			    Integer count = entry.getValue();
			    ps.setLong(ORDER_ID, order.getId());
				ps.setLong(QUALIFICATION_ID, qualification.getId());
				ps.setInt(COUNT, count);
				
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
	public Map<Qualification, Integer> getByOrder(Order order) {
		Connection dbConnection = null;
		PreparedStatement ps = null;
		Map<Qualification, Integer> qualifications = new HashMap<Qualification, Integer>();
		
		try {
			dbConnection = ConnectionPool.getConnection();
			
			ps = dbConnection.prepareStatement(GET_BY_ORDER_ID);
			ps.setLong(ORDER_ID, order.getId());
			
			ResultSet rs = ps.executeQuery();
			System.out.println("qual");
			while ( rs.next() ) {
				Qualification qualification = new Qualification();
				qualification.setId(rs.getLong(1));
				qualification.setTitle(rs.getString(2));
				System.out.println(qualification);
				qualifications.put(qualification, rs.getInt(3));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			ConnectionPool.close(dbConnection);
		}
		return qualifications;
	}
}
