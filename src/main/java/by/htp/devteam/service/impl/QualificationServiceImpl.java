package by.htp.devteam.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.QualificationDao;
import by.htp.devteam.service.QualificationService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.util.ErrorCode;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public final class QualificationServiceImpl implements QualificationService{
	
	/** Logger */
	private static final Logger logger = LogManager.getLogger(QualificationServiceImpl.class.getName());
	
	private QualificationDao qualificationDao;
	
	public QualificationServiceImpl() {
		super();
	}
	
	public QualificationDao getQualificationDao() {
		return qualificationDao;
	}

	public void setQualificationDao(QualificationDao qualificationDao) {
		this.qualificationDao = qualificationDao;
	}

	public List<Qualification> fetchAll() throws ServiceException{
		List<Qualification> qualifications = new ArrayList<>();
		try {
			qualifications = qualificationDao.fetchAll();
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		
		return qualifications;
	}
}
