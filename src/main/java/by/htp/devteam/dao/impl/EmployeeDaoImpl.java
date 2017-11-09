package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.ProjectEmployee;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.EmployeeDao;
import by.htp.devteam.dao.util.ConnectionPool;
import by.htp.devteam.util.HibernateUtil;

import static by.htp.devteam.dao.util.ConstantValue.*;

public final class EmployeeDaoImpl extends HibernateDao implements EmployeeDao {
	
	private static final int ID = 1;
	private static final int NAME = 2;
	private static final int START_WORK = 3;
	private static final int QUALIFICATION_ID = 6;
	private static final int QUALIFICATION_TITLE = 7;
	
	private static final String SQL_IN_CONDITION_MASK = "##";
	
	public EmployeeDaoImpl() {
		super();
	}

	@Override
	public Employee getByUser(User user) throws DaoException {
		Employee employee = null;	
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_EMPLOYEE_GET_BY_USER);
	    	query.setParameter("user", user.getId());
	    	query.setMaxResults(1);
	    	employee = (Employee)query.uniqueResult();

	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_EMPLOYEE_GET_BY_USER, e);
        } finally {
            session.close();
        }
		
		return employee;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getFreeEmployeesForPeriod(Date dateStart, Date dateFinish, Set<Qualification> qualifications) 
			throws DaoException {
		List<Employee> employees = new ArrayList<>();
		java.sql.Date dStart = new java.sql.Date(dateStart.getTime());
		java.sql.Date dFinish = new java.sql.Date(dateFinish.getTime());
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_EMPLOYEE_GET_FREE_FOR_PERIOD);
	    	query.setParameterList("in", getListOfQualificationIds(qualifications));
	    	query.setParameter("start", dStart);
	    	query.setParameter("finish", dFinish);
	    	employees = (List<Employee>) query.list();
	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_EMPLOYEE_GET_FREE_FOR_PERIOD, e);
        } finally {
            session.close();
        }

		return employees;
	}
	
	private List<Long> getListOfQualificationIds(Set<Qualification> qualifications) {
		List<Long> ids = new ArrayList<>();
		for (Qualification qualification : qualifications) {
			ids.add(qualification.getId());
		}

		return ids;
	}
	
	@Override
	public boolean isEmployeesNotBusyForPeriod(Session session, Set<ProjectEmployee> employees, Date dateStart, Date dateFinish) 
			throws DaoException {
		
		int countFreeEmployees = 0;
		int countIds = employees.size();
		boolean isFree = false;
		
	    java.sql.Date dStart = new java.sql.Date(dateStart.getTime());
		java.sql.Date dFinish = new java.sql.Date(dateFinish.getTime());
	    try {
	    	Query query = session.createQuery(SQL_EMPLOYEE_GET_COUNT_FREE_FROM_LIST);
	    	query.setParameterList("in", getListOfEmployeesIds(employees));
	    	query.setParameter("start", dStart);
	    	query.setParameter("finish", dFinish);
	    	query.setMaxResults(1);
	    	countFreeEmployees = ((Long)query.uniqueResult() ).intValue();
	    } catch (HibernateException e) {
            throw new DaoException(MSG_ERROR_EMPLOYEE_GET_COUNT_FREE_FROM_LIST, e);
        }
	    
	    if ( countIds == countFreeEmployees )
			isFree = true;

		return isFree;
	}
	
	private List<Long> getListOfEmployeesIds(Set<ProjectEmployee> employees) {
		List<Long> ids = new ArrayList<>();
		for (ProjectEmployee projectEmployee : employees) {
			ids.add(projectEmployee.getEmployee().getId());
		}

		return ids;
	}

	@Override
	public Map<Long, Integer> getQualificationsIdsAndCountByEmployees(Long[] employeesIds) throws DaoException {
		Map<Long, Integer> qualificationsCount = new HashMap<>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_EMPLOYEE_GET_QUALIFICATIONS_IDS_WITH_COUNTS_BY_EMPLOYEE_IDS);
	    	query.setParameterList("ids", Arrays.asList(employeesIds));
	    	qualificationsCount = getQualificationsCount((List<?>) query.list());
	    	
	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_EMPLOYEE_GET_QUALIFICATIONS_IDS_WITH_COUNTS_BY_EMPLOYEE_IDS, e);
        } finally {
            session.close();
        }

		return qualificationsCount;
	}
	
	private Map<Long, Integer> getQualificationsCount(List<?> list) {
		Map<Long, Integer> qualificationsCount = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			Object[] row = (Object[]) list.get(i);
			qualificationsCount.put((Long) row[0], ((Long) row[1]).intValue());
		}

		return qualificationsCount;
	}
	
	@Override
	public Employee add(Employee employee) throws DaoException {	
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	session.save(employee);
	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_EMPLOYEE_GET_QUALIFICATIONS_IDS_WITH_COUNTS_BY_EMPLOYEE_IDS, e);
        } finally {
            session.close();
        }
		
		return employee;
	}
	
	@Override
	public Employee getById(Long id) throws DaoException, ObjectNotFoundException {
		Employee employee = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_EMPLOYEE_GET_BY_ID) ) {

			ps.setLong(ID, id);
			employee = executeQueryAndGetEmployeeFromResultSet(ps);
		} catch ( SQLException e ) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_GET_BY_ID, e);
		}
		
		return employee;
	}
	
	private Employee executeQueryAndGetEmployeeFromResultSet(PreparedStatement ps) 
			throws SQLException, ObjectNotFoundException {
		Employee employee = new Employee();
		try ( ResultSet rs = ps.executeQuery() ) {
			if ( rs.next() ) {
				employee.setId(rs.getLong(1));
				employee.setName(rs.getString(2));
				employee.setStartWork(rs.getDate(3));				
			} else {
				throw new ObjectNotFoundException(MSG_EMPLOYEE_NOT_FOUND);
			}
		}
		
		return employee;
	}

	@Override
	public void setUserForEmployee(Connection connection, Employee employee, User user) throws DaoException {
		try ( PreparedStatement ps = connection.prepareStatement(SQL_EMPLOYEE_SET_USER) ) {
			ps.setLong(1, user.getId());
			ps.setLong(2, employee.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_SET_USER, e);
		}		
	}

	@Override
	public boolean isExistUserForEmployee(Connection connection, Employee employee) throws DaoException {
		boolean isExist = true;
		try ( PreparedStatement st = connection.prepareStatement(SQL_EMPLOYEE_GET_USER_ID) ) {

			st.setLong(1, employee.getId());
			try ( ResultSet rs = st.executeQuery() ) {
				if ( rs.next() && rs.getInt(1) == 0 )
					isExist = false;
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_GET_USER, e);
		}

		return isExist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PagingVo<Employee> fetchAll(int offset, int countPerPage) throws DaoException {
		PagingVo<Employee> pagingVo = new PagingVo<>();		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    List<Employee> employees = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_EMPLOYEE_FETCH_ALL_WITH_USER);
	    	query.setFirstResult(offset);
	    	query.setMaxResults(countPerPage);
	    	employees = (List<Employee>) query.list();
	    	pagingVo.setRecords(employees);
	    	
	    	Query  query1 = session.createQuery(SQL_EMPLOYEE_FETCH_COUNT_ALL_WITH_USER);
	    	query1.setMaxResults(1);
	    	pagingVo.setCountAllRecords( ((Long)query1.uniqueResult() ).intValue());
	    	
	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_EMPLOYEE_LIST, e);
        } finally {
            session.close();
        }
		
		return pagingVo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getListWithNotSetUser() throws DaoException {
		List<Employee> employees = new ArrayList<>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_EMPLOYEE_FETCH_NO_USER);
	    	employees = (List<Employee>) query.list();

	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_EMPLOYEE_LIST_NOT_USER, e);
        } finally {
            session.close();
        }
		return employees;
	}

}
