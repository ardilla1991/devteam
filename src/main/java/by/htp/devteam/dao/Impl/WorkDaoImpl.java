package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Work;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.WorkDao;

public class WorkDaoImpl implements WorkDao{

	private final int ID = 1;
	private final int TITLE = 2;
	private final int DESCRIPTION = 3;
	private final int PRICE = 4;
	
	private final static String FETCH_ALL = "SELECT * FROM work ORDER BY title DESC";
	
	public List<Work> fetchAll() {
		List<Work> works = new ArrayList<Work>();
		
		Connection dbConnection = null;
		Statement st = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			
			st = dbConnection.createStatement();
			ResultSet rs = st.executeQuery(FETCH_ALL);
			
			while ( rs.next() ) {
				Work work = new Work();
				work.setId(rs.getLong(ID));
				work.setTitle(rs.getString(TITLE));
				work.setDescription(rs.getString(DESCRIPTION));
				work.setPrice(rs.getInt(PRICE));
				
				works.add(work);
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(st);
			ConnectionPool.close(dbConnection);
		}
		return works;
	}
	
	private void close(Statement st) {
		if ( st != null ) {
			try {
				st.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
}
