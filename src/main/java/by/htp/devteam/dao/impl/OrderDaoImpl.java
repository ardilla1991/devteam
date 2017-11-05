package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.OrderQualification;
import by.htp.devteam.bean.OrderWork;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.OrderDao;
import by.htp.devteam.dao.util.ConnectionPool;
import by.htp.devteam.util.HibernateUtil;

import static by.htp.devteam.dao.util.ConstantValue.*;

public final class OrderDaoImpl implements OrderDao {

	private static final int ID = 1;
	private static final int TITLE = 2;
	private static final int DESCRIPTION = 3;
	private static final int SPECIFICATION = 4;
	private static final int CUSTOMER_ID = 5;
	private static final int DATE_CREATED = 6;
	private static final int DATE_START = 7;
	private static final int DATE_FINISH = 8;
	private static final int DATE_PROCESSING = 9;
	private static final int PRICE = 10;

	private static final int ORDER_ID = 1;
	private static final int WORK_ID = 2;
	
	private static final int QUALIFICATION_ID = 2;
	private static final int COUNT = 3;
	
	public OrderDaoImpl() {
		super();
	}
	
	/*
	 * Order by dateStart DESC
	 * @see by.htp.devteam.dao.OrderDao#getNewOrders(int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PagingVo<Order> getNewOrders(int offset, int countPerPage) throws DaoException {
		PagingVo<Order> pagingVo = new PagingVo<>();
	
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    List<Order> orders = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query  query = session.createQuery(SQL_ORDER_NEW_RECORDS_LIST);
	    	query.setFirstResult(offset);
	    	query.setMaxResults(countPerPage);
	    	orders = (List<Order>) query.list();

	    	pagingVo.setRecords(orders);
	    	
	    	Query  query1 = session.createQuery(SQL_ORDER_COUNT);
	    	query1.setMaxResults(1);
	    	pagingVo.setCountAllRecords( ((Long)query1.uniqueResult() ).intValue());
	    	
	    	tx.commit();
	    } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_ORDER_NEW_RECORDS_LIST, e);
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
	public OrderVo getById(long id) throws DaoException, ObjectNotFoundException {
		OrderVo orderVo = new OrderVo();
		Order order = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_ORDER_GET_BY_ID);
	    	query.setParameter("id", id);
	    	query.setMaxResults(1);
	    	order = (Order)query.uniqueResult();
	    	tx.commit();
	    	
	    	orderVo.setOrder(order);
		    orderVo.setQualifications(getQualifications(order.getQualifications()));
	    	orderVo.setWorks(getWorks(order.getWorks()));
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_ORDER_GET_BY_ID, e);
        } finally {
            session.close();
        }
	    
		return orderVo;
	}
	
	private List<Work> getWorks(Set<OrderWork> worksProxy) {
		List<Work> works = new ArrayList<>();
		for (OrderWork qual : worksProxy) {
    		works.add(qual.getWork());
    	}
		return works;
	}
	
	private Map<Qualification, Integer> getQualifications(Set<OrderQualification> qualificationsProxy) {
    	Map<Qualification, Integer> qualifications = new HashMap<>();
    	for (OrderQualification qual : qualificationsProxy) {
    		qualifications.put(qual.getQualification(), qual.getCount());
    	}
		return qualifications;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getByCustomer(Customer customer) throws DaoException{
		List<Order> orders = new ArrayList<>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_ORDER_GET_LIST_BY_CUSTOMER_ID);
	    	query.setParameter("customer_id", customer.getId());
	    	orders = (List<Order>) query.list();
	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_ORDER_GET_LIST_BY_CUSTOMER_ID, e);
        } finally {
            session.close();
        }
		
		return orders;
	}

	@Override
	public OrderVo add(OrderVo orderVo) throws DaoException{
		Connection dbConnection = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			dbConnection.setAutoCommit(false);
			Long id = addOrder(dbConnection, orderVo.getOrder());
			orderVo.getOrder().setId(id);
			addWorks(dbConnection, orderVo.getOrder(), orderVo.getWorks());
			addQualifications(dbConnection, orderVo.getOrder(), orderVo.getQualifications());
			dbConnection.commit();
		} catch (SQLException e) {
			rollback(dbConnection);
			ConnectionPool.returnConnection(dbConnection);
			throw new DaoException(MSG_ERROR_ORDER_ADD, e);
		}
		return orderVo;
	}
	
	private Long addOrder(Connection dbConnection, Order order) throws SQLException {
		Long id = 0L;
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_ADD, PreparedStatement.RETURN_GENERATED_KEYS) ) {
			prepareStatementForOrder(ps, order);
			ps.executeUpdate();
			try ( ResultSet rs = ps.getGeneratedKeys() ) {
				if (rs.next()) {
				    id  = rs.getLong(ID);
				}
			}
		}
		
		return id;
	}
	
	/*
	 * Prepare batches for table-connection order-work
	 */
	private void addWorks(Connection dbConnection, Order order, List<Work> works) throws SQLException {
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_ADD_WORK) ) {

			prepareAndAddBatchesForWorks(ps, order, works);
			ps.executeBatch();
		}
	}
	
	/*
	 * Prepare batches for table-connection order-qualification
	 */
	private void addQualifications(Connection dbConnection, Order order, 
								   Map<Qualification, Integer> qualifications) throws SQLException {
		try ( PreparedStatement ps = dbConnection.prepareStatement(SQL_ORDER_ADD_QUALIFICATION) ) {

			prepareAndAddBatchesForQualifications(ps, order, qualifications);
			ps.executeBatch();
		}
	}
	
	private void prepareAndAddBatchesForWorks(PreparedStatement ps, Order order, 
											  List<Work> works) throws SQLException {
		for ( Work work : works ) {
			ps.setLong(ORDER_ID, order.getId());
			ps.setLong(WORK_ID, work.getId());
			
			ps.addBatch();
		}
	}
	
	private void prepareAndAddBatchesForQualifications(PreparedStatement ps, Order order, 
													   Map<Qualification, Integer> qualifications) 
			throws SQLException {
		for (Map.Entry<Qualification, Integer> entry : qualifications.entrySet()) {
		    ps.setLong(ORDER_ID, order.getId());
			ps.setLong(QUALIFICATION_ID, entry.getKey().getId());
			ps.setInt(COUNT, entry.getValue());
			
			ps.addBatch();
		}
	}
	
	@Override
	public void setPriceAndDateProcessing(Connection connection, Order order) throws DaoException {
		try ( PreparedStatement ps = connection.prepareStatement(SQL_ORDER_SET_PRICE) ) {
			ps.setBigDecimal(1, order.getPrice());
			ps.setTimestamp(2,  new java.sql.Timestamp(order.getDateProcessing().getTime()));
			ps.setLong(3, order.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ORDER_SET_PRICE, e);
		}	
	}
	
	/*
	 * rollback query
	 */
	private void rollback(Connection dbConnection) throws DaoException {
		try {
			dbConnection.rollback();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ROLLBACK);
		}
	}
	
	private void prepareStatementForOrder(PreparedStatement ps, Order order) throws SQLException {
		ps.setString(ID, null);
		ps.setString(TITLE, order.getTitle());
		ps.setString(DESCRIPTION, order.getDescription());
		ps.setString(SPECIFICATION, order.getSpecification());
		ps.setLong(CUSTOMER_ID, order.getCustomer().getId());
		ps.setTimestamp(DATE_CREATED, new java.sql.Timestamp(order.getDateCreated().getTime()));
		ps.setDate(DATE_START, new java.sql.Date(order.getDateStart().getTime()));
		ps.setDate(DATE_FINISH, new java.sql.Date(order.getDateFinish().getTime()));
		ps.setBigDecimal(9, order.getPrice());
	}


}
