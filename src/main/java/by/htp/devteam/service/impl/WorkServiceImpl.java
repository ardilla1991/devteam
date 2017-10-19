package by.htp.devteam.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Work;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.WorkDao;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.WorkService;
import by.htp.devteam.service.util.ErrorCode;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public final class WorkServiceImpl implements WorkService{
	
	/** Logger */
	private static final Logger logger = LogManager.getLogger(WorkServiceImpl.class.getName());
	
	/** DAO object */
	private WorkDao workDao;
	
	public WorkServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		workDao = daoFactory.getWorkDao();
	}

	@Override
	public List<Work> fetchAll() throws ServiceException {
		List<Work> works = new ArrayList<>();
		try {
			works = workDao.fetchAll();
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		
		return works;
	}

}
