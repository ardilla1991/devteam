package by.htp.devteam.command.command;

import static by.htp.devteam.util.PageConctantValue.PAGE_ERROR;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

import by.htp.devteam.util.PageConctantValue;
import by.htp.devteam.util.RequestParamConstantValue;
import by.htp.devteam.util.SettingConstantValue;

public class ProjectNewListAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = PageConctantValue.PAGE_DEFAULT;
		
		String currPage = request.getParameter(RequestParamConstantValue.PAGE);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		List<Project> projects = null;
		try {
			ProjectDto projectDto = projectService.getNewProjects(currPage);
			projects = projectDto.getProjects();

			request.setAttribute(RequestParamConstantValue.PROJECT_LIST, projects);
			request.setAttribute(RequestParamConstantValue.CURR_PAGE, projectDto.getCurrPage());
			request.setAttribute(RequestParamConstantValue.COUNT_PAGES, projectDto.getCountPages());
	
			page = PageConctantValue.PROJECT_NEW_LIST;
		} catch (ServiceException e) {
			page = PAGE_ERROR;
			request.setAttribute(RequestParamConstantValue.ERROR_MSG, e.getMessage());
		}
		
		return page;
	}

	
}
