package by.htp.devteam.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import by.htp.devteam.bean.Work;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.WorkDao;
import by.htp.devteam.util.HibernateUtil;

import static by.htp.devteam.dao.util.ConstantValue.*;

public final class WorkDaoImpl extends HibernateDao implements WorkDao {
	
	public WorkDaoImpl() {
		super();
	}
	
	/*
	 * Get all records sorted by title desc
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Work> fetchAll() throws DaoException {
		List<Work> works = new ArrayList<>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_WORK_FETCH_ALL);
	    	works = (List<Work>)query.list();

	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_WORK_FETCH_ALL, e);
        } finally {
            session.close();
        }

		return works;
	}
}
