package by.htp.devteam.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.QualificationDao;
import by.htp.devteam.util.HibernateUtil;

import static by.htp.devteam.dao.util.ConstantValue.*;

@Repository("qualificationDao")
public final class QualificationDaoImpl extends HibernateDao implements QualificationDao {
	
	private static final int ID = 1;
	private static final int TITLE = 2;
	
	public QualificationDaoImpl() {
		super();
	}
	
	/*
	 * Get all records sorted by title desc
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Qualification> fetchAll() throws DaoException {
		List<Qualification> qualifications = new ArrayList<>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	    	tx = session.getTransaction();
	    	tx.begin();
	    	Query query = session.createQuery(SQL_QUALIFICATION_FETCH_ALL);
	    	qualifications = (List<Qualification>)query.list();

	    	tx.commit();
	    } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(MSG_ERROR_QUALIFICATION_FETCH_ALL, e);
        } finally {
            session.close();
        }
		
		return qualifications;
	}
}
