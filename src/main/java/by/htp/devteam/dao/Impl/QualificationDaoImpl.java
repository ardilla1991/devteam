package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.QualificationDao;

public class QualificationDaoImpl extends CommonDao implements QualificationDao{
	
	private final int ID = 1;
	private final int TITLE = 2;
	
	private final String FETCH_ALL = "SELECT * FROM qualification";
	
	public List<Qualification> fetchAll() {
		List<Qualification> qualifications = new ArrayList<Qualification>();
		try (Connection dbConnection = ConnectionPool.getConnection();
				Statement st = dbConnection.createStatement();
				ResultSet rs = st.executeQuery(FETCH_ALL)) {

			qualifications = getQualificationListFromResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qualifications;
	}
	
	private List<Qualification> getQualificationListFromResultSet(ResultSet rs) throws SQLException {
		List<Qualification> qualifications = new ArrayList<Qualification>();
		while (rs.next()) {
			Qualification qualification = new Qualification();
			qualification.setId(rs.getLong(ID));
			qualification.setTitle(rs.getString(TITLE));
	
			qualifications.add(qualification);
		}
		
		return qualifications;
	}
}
