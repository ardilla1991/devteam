package by.htp.devteam.service.impl;

import java.util.List;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.QualificationDao;
import by.htp.devteam.service.QualificationService;
import by.htp.devteam.service.ServiceException;

public class QualificationServiceImpl implements QualificationService{
	
	private QualificationDao qualificationDao;
	
	public QualificationServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		qualificationDao = daoFactory.getQualificationDao();
	}
	
	public List<Qualification> fetchAll() throws ServiceException{
		List<Qualification> qualifications = null;
		try {
			qualifications = qualificationDao.fetchAll();
		} catch (DaoException e) {
			throw new ServiceException("service error", e);
		}
		
		return qualifications;
	}
}
