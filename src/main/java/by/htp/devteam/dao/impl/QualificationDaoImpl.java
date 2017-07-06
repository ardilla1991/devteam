package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.QualificationDao;

import static by.htp.devteam.dao.util.ConstantValue.*;

public class QualificationDaoImpl implements QualificationDao{
	
	private final static int ID = 1;
	private final static int TITLE = 2;
	
	
	
	public List<Qualification> fetchAll() throws DaoException {
		List<Qualification> qualifications = new ArrayList<Qualification>();
		try (Connection dbConnection = ConnectionPool.getConnection();
				Statement st = dbConnection.createStatement() ) {

			qualifications = getQualificationListFromResultSet(st);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_QUALIFICATION_FETCH_ALL, e);
		}
		
		return qualifications;
	}
	
	private List<Qualification> getQualificationListFromResultSet(Statement st) throws SQLException {
		List<Qualification> qualifications = new ArrayList<Qualification>();
		try ( ResultSet rs = st.executeQuery(SQL_QUALIFICATION_FETCH_ALL) ) {
			while (rs.next()) {
				Qualification qualification = new Qualification();
				qualification.setId(rs.getLong(ID));
				qualification.setTitle(rs.getString(TITLE));
		
				qualifications.add(qualification);
			}
		}
		
		return qualifications;
	}
}
