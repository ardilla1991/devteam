package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

/**
 * Action for display all projects
 * Logging information about who does action
 * @author julia
 *
 */
public class ProjectListAction implements CommandAction{
	
	public ProjectListAction() {
		super();
	}
	
	@Override
	public Page executeGET(HttpServletRequest request, HttpServletResponse response) {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);
		
		try {
			PagingVo<Project> pagingVo = projectService.fetchAll(currPage, null);
			
			request.setAttribute(REQUEST_PARAM_URI, PAGE_PROJECT_LIST_URI);
			request.setAttribute(REQUEST_PARAM_CURR_PAGE, pagingVo.getCurrPage());
			request.setAttribute(REQUEST_PARAM_COUNT_PAGES, pagingVo.getCountPages());
			request.setAttribute(REQUEST_PARAM_PROJECT_LIST, pagingVo.getRecords());
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_PROJECT_LIST);
	}

	@Override
	public Page executePOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		// TODO Auto-generated method stub
		return null;
	}

}
