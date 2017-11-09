package by.htp.devteam.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateDao {
	
	private SessionFactory sessionFactory;

    public HibernateDao() {
        super();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
}
