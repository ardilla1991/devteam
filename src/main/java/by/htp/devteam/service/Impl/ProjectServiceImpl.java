package by.htp.devteam.service.Impl;

import java.util.List;

import by.htp.devteam.bean.Project;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.service.ProjectService;

public class ProjectServiceImpl implements ProjectService{

	private ProjectDao projectDao;
	
	public ProjectServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		projectDao = daoFactory.getProjectDao();
	}
	@Override
	
	public List<Project> getNewProjects() {
		
		return projectDao.getNewProjects();
	}

}
