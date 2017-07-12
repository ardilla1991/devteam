package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static by.htp.devteam.command.util.ConstantValue.*;

import java.util.List;

/**
 * Action for find project
 * Logging information about who does action
 * @author julia
 *
 */
public class ProjectFindAction implements CommandAction{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(ProjectFindAction.class.getName());
	
	public ProjectFindAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String page = PAGE_PROJECT_FIND_AJAX;
		String title = request.getParameter(REQUEST_PARAM_PROJECT_TITLE);
		
		logging(request, title);
		
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
	
	private void logging(HttpServletRequest request, String title ) {
		HttpSession session = request.getSession(false);
		UserVo userVo = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_PROJECT_FIND, userVo.getUser().getLogin(), title);
	}
	
}
