package by.htp.devteam.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.QualificationDao;
import by.htp.devteam.service.QualificationService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.util.ErrorCodeEnum;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class QualificationServiceImpl implements QualificationService{
	
	private QualificationDao qualificationDao;
	private static final Logger logger = LogManager.getLogger(QualificationServiceImpl.class.getName());
	
	public QualificationServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		qualificationDao = daoFactory.getQualificationDao();
	}
	
	public List<Qualification> fetchAll() throws ServiceException{
		List<Qualification> qualifications = new ArrayList<Qualification>();
		try {
			qualifications = qualificationDao.fetchAll();
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}
		
		return qualifications;
	}
}
