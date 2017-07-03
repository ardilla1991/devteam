package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Work;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.WorkDao;

public class WorkDaoImpl extends CommonDao implements WorkDao{

	private final static int ID = 1;
	private final static int TITLE = 2;
	private final static int DESCRIPTION = 3;
	private final static int PRICE = 4;
	
	private final static String FETCH_ALL = "SELECT * FROM work ORDER BY title DESC";
	
	@Override
	public List<Work> fetchAll() throws DaoException{
		List<Work> works = new ArrayList<Work>();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				Statement st = dbConnection.createStatement() ) {
		
			works = getWorkListFromResultSet(st);
		} catch (SQLException e) {
			throw new DaoException("sql error", e);
		}
		return works;
	}
	
	private List<Work> getWorkListFromResultSet(Statement st) throws SQLException{
		List<Work> works = new ArrayList<Work>();
		try ( ResultSet rs = st.executeQuery(FETCH_ALL) ) {
			while ( rs.next() ) {
				Work work = new Work();
				work.setId(rs.getLong(ID));
				work.setTitle(rs.getString(TITLE));
				work.setDescription(rs.getString(DESCRIPTION));
				work.setPrice(rs.getInt(PRICE));
				
				works.add(work);
			}
		}
		
		return works;
	}
}
