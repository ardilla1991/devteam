package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Action to update hours count for project spending by employee.
 * Logging information about who does action
 * @author julia
 *
 */
public class ProjectUpdateHoursAction implements CommandAction{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(ProjectUpdateHoursAction.class.getName());
	
	public ProjectUpdateHoursAction() {
		super();
	}
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String page = PAGE_PROJECT_LIST_BY_EMPLOYEE_URI;
		boolean isRedirect = true;

		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);
		String hours = request.getParameter(REQUEST_PARAM_PROJECT_HOURS);

		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_PROJECT_UPDATE_HOURS, userVO.getUser().getLogin(), id);
		
		try {
			projectService.updateHours(id, userVO.getEmployee(), hours);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_PROJECT_ID, id);
			request.setAttribute(REQUEST_PARAM_PROJECT_HOURS, hours);
			
			page = PAGE_PROJECT_VIEW_BY_ID_URI + id;
			isRedirect = false;			
		}
		
		return new Page(page, isRedirect);
	}
	
}
