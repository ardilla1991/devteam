package by.htp.devteam.service.impl;

import java.util.List;

import by.htp.devteam.bean.Work;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.WorkDao;
import by.htp.devteam.service.WorkService;

public class WorkServiceImpl implements WorkService{
	
	private WorkDao workDao;
	
	public WorkServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		workDao = daoFactory.getWorkDao();
	}

	@Override
	public List<Work> fetchAll() {
		
		return workDao.fetchAll();
	}

}
