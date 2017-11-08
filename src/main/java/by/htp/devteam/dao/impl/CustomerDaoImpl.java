package by.htp.devteam.dao.impl;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.User;
import by.htp.devteam.dao.CustomerDao;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.util.HibernateUtil;

import static by.htp.devteam.dao.util.ConstantValue.*;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;

public final class CustomerDaoImpl implements CustomerDao {

	private static final int ID = 1;
	private static final int NAME = 2;
	private static final int EMAIL = 3;
	private static final int PHONE = 4;
	
	private static final Logger logger = LogManager.getLogger(CustomerDaoImpl.class.getName());
	
	public CustomerDaoImpl() {
		super();
	}

	@Override
	public Customer getCustomerByUser(User user) throws DaoException {
		Customer customer = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_CUSTOMER_GET_BY_USER);
	    	query.setParameter("user", user.getId());
	    	query.setMaxResults(1);
	    	customer = (Customer)query.uniqueResult();

	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_CUSTOMER_GET_BY_USER, e);
        } finally {
            session.close();
        }
		
		return customer;
	}

}
