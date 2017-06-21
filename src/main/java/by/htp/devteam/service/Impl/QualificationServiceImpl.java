package by.htp.devteam.service.Impl;

import java.util.List;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.QualificationDao;
import by.htp.devteam.dao.WorkDao;
import by.htp.devteam.service.QualificationService;

public class QualificationServiceImpl implements QualificationService{
	
	private QualificationDao qualificationDao;
	
	public QualificationServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		qualificationDao = daoFactory.getQualificationDao();
	}
	
	public List<Qualification> fetchAll() {
		return qualificationDao.fetchAll();
	}
}
