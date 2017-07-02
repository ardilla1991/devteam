package by.htp.devteam.command.user;

import static by.htp.devteam.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

public class ProjectViewAction implements CommandAction{

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);
		ProjectDto projectDto = new ProjectDto();
		try {
			projectDto = projectService.getById(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute(REQUEST_PARAM_PROJECT_DTO, projectDto);
		
		return new Page(PAGE_PROJECT_VIEW);
	}
	
}
