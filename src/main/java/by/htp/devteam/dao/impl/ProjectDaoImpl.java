package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.dao.util.ConnectionPool;
import by.htp.devteam.util.HibernateUtil;

import static by.htp.devteam.dao.util.ConstantValue.*;

public final class ProjectDaoImpl implements ProjectDao {
	
	private static final int ID = 1;
	private static final int TITLE = 2;
	private static final int DESCRIPTION = 3;
	private static final int DATE_CREATED = 4;
	private static final int ORDER_ID = 5;
	
	private static final String SQL_LIKE_CONDITION_PERCENT = "%";
	
	public ProjectDaoImpl() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PagingVo<Project> fetchAll(int offset, int countPerPage, Employee employee) throws DaoException {
		if ( employee == null )
			return fetchAll(offset, countPerPage);
		
		PagingVo<Project> pagingVo = new PagingVo<>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    List<Project> projects = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_PROJECT_LIST_BY_EMPLOYEE);
	    	query.setParameter("employee_id", employee.getId());
	    	query.setFirstResult(offset);
	    	query.setMaxResults(countPerPage);
	    	projects = (List<Project>) query.list();
	    	pagingVo.setRecords(projects);
	    	
	    	Query  query1 = session.createQuery(SQL_PROJECT_COUNT_BY_EMPLOYEE);
	    	query1.setParameter("employee_id", employee.getId());
	    	query1.setMaxResults(1);
	    	pagingVo.setCountAllRecords( ((Long)query1.uniqueResult() ).intValue());
	    	
	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_PROJECT_LIST_BY_EMPLOYEE, e);
        } finally {
            session.close();
        }
		
		return pagingVo;
	}
	
	/*
	 * Get all list of projects 
	 */
	@SuppressWarnings("unchecked")
	private PagingVo<Project> fetchAll(int offset, int countPerPage) throws DaoException {
		PagingVo<Project> pagingVo = new PagingVo<>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    List<Project> projects = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_PROJECT_FETCH_ALL);
	    	query.setFirstResult(offset);
	    	query.setMaxResults(countPerPage);
	    	projects = (List<Project>) query.list();
	    	pagingVo.setRecords(projects);
	    	Query  query1 = session.createQuery(SQL_PROJECT_COUNT);
	    	query1.setMaxResults(1);
	    	pagingVo.setCountAllRecords( ((Long)query1.uniqueResult() ).intValue());
	    	
	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_PROJECT_FETCH_ALL, e);
        } finally {
            session.close();
        }
		
		return pagingVo;
	}
	
	private Date getDateFromTimestamp(Timestamp timestamp) {
		Date date = null;
		if (timestamp != null)
		    date = new Date(timestamp.getTime());

		return date;
	}
	
	@Override
	public Project add(Connection connection, Project project) throws DaoException {
		Project createdProject = project;
		try ( PreparedStatement ps = connection.prepareStatement(SQL_PROJECT_ADD, PreparedStatement.RETURN_GENERATED_KEYS) ) {

			prepareStatementForProject(ps, project);
			ps.executeUpdate();
			try ( ResultSet rs = ps.getGeneratedKeys() ) {
				if (rs.next()) {
					createdProject.setId(rs.getLong(ID));
				}
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_ADD, e);
		}
		return createdProject;
	}
	
	private void prepareStatementForProject(PreparedStatement ps, Project project) throws SQLException {
		ps.setString(ID, null);
		ps.setString(TITLE, project.getTitle());
		ps.setString(DESCRIPTION, project.getDescription());
		ps.setTimestamp(DATE_CREATED, new java.sql.Timestamp(project.getDateCreated().getTime()));
		ps.setLong(ORDER_ID, project.getOrder().getId());
	}

	@Override
	public void setEmployees(Connection connection, Project project, Long[] employeeIds) throws DaoException {
		try ( PreparedStatement ps = connection.prepareStatement(SQL_PROJECT_ADD_EMPLOYEE) ) {

			prepareAndAddBatchesForEmployee(ps, project, employeeIds);
			ps.executeBatch();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_ADD_EMPLOYEE, e);
		}
	}
	
	private void prepareAndAddBatchesForEmployee(PreparedStatement ps, Project project, Long[] ids) throws SQLException {
		for ( Long id : ids ) {
			ps.setLong(1, project.getId());
			ps.setLong(2, id);
			
			ps.addBatch();
		}
	}

	@Override
	public Project getById(Long id) throws DaoException, ObjectNotFoundException {
		Project project = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_PROJECT_GET_BY_ID);
	    	query.setParameter("id", id);
	    	query.setMaxResults(1);
	    	project = (Project)query.uniqueResult();
	    	
	    	//System.out.println("project==");
	    	//System.out.println(project);
	    	tx.commit();
	    	Hibernate.initialize(project.getOrder().getCustomer());
	    	Hibernate.initialize(project.getEmployees());
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_PROJECT_GET_BY_ID, e);
        } finally {
            session.close();
        }
		
		return project;
	}
	
	@Override
	public void updateHours(Project project, Employee employee, int hours) throws DaoException {
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_PROJECT_UPDATE_HOURS) ) {

			ps.setInt(1, hours);
			ps.setLong(2, project.getId());
			ps.setLong(3, employee.getId());
			ps.executeUpdate();
				
		} catch ( SQLException e ) {
			throw new DaoException(MSG_ERROR_PROJECT_UPDATE_HOURS, e);
		}
		
	}

	@Override
	public List<Project> findByTitle(String title) throws DaoException {
		List<Project> projects = new ArrayList<>();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_PROJECT_FIND_BY_TITLE) ) {

			ps.setString(1, SQL_LIKE_CONDITION_PERCENT + title + SQL_LIKE_CONDITION_PERCENT);
			try ( ResultSet rs = ps.executeQuery() ) {
				while ( rs.next() ) {					
					Project project = new Project();
					project.setId(rs.getLong(ID));
					project.setTitle(rs.getString(TITLE));
					project.setDescription(rs.getString(DESCRIPTION));
					project.setDateCreated(rs.getDate(DATE_CREATED));
					
					projects.add(project);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_FIND_BY_TITLE, e);
		}
		
		return projects;
	}
	
	@Override
	public Connection startTransaction() throws DaoException {
		Connection dbConnection = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			dbConnection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_CONNECTION, e);
		}
		return dbConnection;
	}
	
	@Override
	public void rollbackTransaction(Connection connection) throws DaoException {
		try {
			connection.rollback();
			ConnectionPool.returnConnection(connection);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ROLLBACK, e);
		}
	}
	
	@Override
	public void commitTransaction(Connection connection) throws DaoException {
		try {
			connection.commit();
			ConnectionPool.returnConnection(connection);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_COMMIT, e);
		}
	}

}
