package by.htp.devteam.service.impl;

import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.util.SettingConstantValue;

public class ProjectServiceImpl implements ProjectService{

	private ProjectDao projectDao;
	
	public ProjectServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		projectDao = daoFactory.getProjectDao();
	}
	
	@Override
	public ProjectDto getNewProjects(String currPage) throws ServiceException{
		int countPerPage = SettingConstantValue.COUNT_PER_PAGE;
		int currPageValue = 0;
		
		currPageValue = ( currPage == null 
					  ? SettingConstantValue.START_PAGE 
					  : Integer.valueOf(currPage) );
		
		if ( currPageValue == 0 )
			throw new ServiceException("page not found");
		
		int offset = (currPageValue - 1 ) * countPerPage;
			
		ProjectDto projectDto = projectDao.getNewProjects(offset, countPerPage);

		int countPages = (int) Math.ceil(projectDto.getCountRecords() * 1.0 / countPerPage);
		projectDto.setCountPages(countPages);
		projectDto.setCurrPage(currPageValue);
		
		return projectDto;
	}

}
