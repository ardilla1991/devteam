package by.htp.devteam.command.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import static by.htp.devteam.util.admin.AdminPageConstantValue.*;
import by.htp.devteam.util.admin.AdminRequestParamConstantValue;

public class AdminProjectNewListAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = PAGE_DEFAULT;
		
		String currPage = request.getParameter(AdminRequestParamConstantValue.PAGE);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		List<Project> projects = null;
		try {
			ProjectDto projectDto = projectService.getNewProjects(currPage);
			projects = projectDto.getProjects();

			request.setAttribute(AdminRequestParamConstantValue.PROJECT_LIST, projects);
			request.setAttribute(AdminRequestParamConstantValue.CURR_PAGE, projectDto.getCurrPage());
			request.setAttribute(AdminRequestParamConstantValue.COUNT_PAGES, projectDto.getCountPages());
	
			page = null;
		} catch (ServiceException e) {
			page = PAGE_ERROR;
			request.setAttribute(AdminRequestParamConstantValue.ERROR_MSG, e.getMessage());
		}
		
		return page;
	}

	
}
