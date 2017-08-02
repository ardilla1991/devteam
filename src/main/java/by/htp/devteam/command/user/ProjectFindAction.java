package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Project;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

import static by.htp.devteam.command.util.ConstantValue.*;

import java.util.List;

/**
 * Action for find project
 * Logging information about who does action
 * @author julia
 *
 */
public class ProjectFindAction implements CommandAction{
	
	public ProjectFindAction() {
		super();
	}
	
	@Override
	public Page executeGET(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String page = PAGE_PROJECT_FIND_AJAX;
		String title = request.getParameter(REQUEST_PARAM_PROJECT_TITLE);

		try {
			List<Project> project = projectService.findByTitle(title);
			
			request.setAttribute(REQUEST_PARAM_PROJECT_LIST, project);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		if ( request.getParameter(REQUEST_PARAM_XHR) == null)
			page = PAGE_PROJECT_FIND;
		
		return new Page(page);
	}

	@Override
	public Page executePOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
