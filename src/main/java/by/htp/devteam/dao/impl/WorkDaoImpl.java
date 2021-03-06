package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Work;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.WorkDao;
import by.htp.devteam.dao.util.ConnectionPool;

import static by.htp.devteam.dao.util.ConstantValue.*;

public final class WorkDaoImpl implements WorkDao {

	private static final int ID = 1;
	private static final int TITLE = 2;
	private static final int DESCRIPTION = 3;
	
	public WorkDaoImpl() {
		super();
	}
	
	/*
	 * Get all records sorted by title desc
	 */
	@Override
	public List<Work> fetchAll() throws DaoException {
		List<Work> works = new ArrayList<>();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				Statement st = dbConnection.createStatement() ) {
		
			works = executeQueryAndGetWorkListFromResultSet(st);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_WORK_FETCH_ALL, e);
		}
		return works;
	}
	
	private List<Work> executeQueryAndGetWorkListFromResultSet(Statement st) throws SQLException {
		List<Work> works = new ArrayList<>();
		try ( ResultSet rs = st.executeQuery(SQL_WORK_FETCH_ALL) ) {
			while ( rs.next() ) {
				Work work = new Work();
				work.setId(rs.getLong(ID));
				work.setTitle(rs.getString(TITLE));
				work.setDescription(rs.getString(DESCRIPTION));
				
				works.add(work);
			}
		}
		
		return works;
	}
}
