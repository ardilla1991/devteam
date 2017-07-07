package by.htp.devteam.service.impl;

import java.util.List;

import by.htp.devteam.bean.Work;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.WorkDao;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.WorkService;
import by.htp.devteam.service.util.ErrorCodeEnum;

public class WorkServiceImpl implements WorkService{
	
	private WorkDao workDao;
	
	public WorkServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		workDao = daoFactory.getWorkDao();
	}

	@Override
	public List<Work> fetchAll() throws ServiceException {
		List<Work> works = null;
		try {
			works = workDao.fetchAll();
		} catch ( DaoException e ) {
			e.printStackTrace();
			///Logger
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}
		
		return works;
	}

}
