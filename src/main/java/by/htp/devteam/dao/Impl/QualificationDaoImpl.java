package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.dao.QualificationDao;

public class QualificationDaoImpl implements QualificationDao{
	
	private final int ID = 1;
	private final int TITLE = 2;
	
	private final String FETCH_ALL = "SELECT * FROM qualification";
	
	public List<Qualification> fetchAll() {
		List<Qualification> qualifications = new ArrayList<Qualification>();
		
		Connection dbConnection = null;
		Statement st = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			
			st = dbConnection.createStatement();
			ResultSet rs = st.executeQuery(FETCH_ALL);
			
			while ( rs.next() ) {
				Qualification qualification = new Qualification();
				qualification.setId(rs.getLong(ID));
				qualification.setTitle(rs.getString(TITLE));
				
				qualifications.add(qualification);
			}
			rs.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(st);
			ConnectionPool.close(dbConnection);
		}
		return qualifications;
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
