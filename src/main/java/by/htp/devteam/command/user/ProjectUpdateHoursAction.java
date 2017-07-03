package by.htp.devteam.command.user;

import static by.htp.devteam.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.dto.OrderVo;
import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

public class ProjectUpdateHoursAction implements CommandAction{

	private ProjectService projectService;
	
	public ProjectUpdateHoursAction() {
		super();
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		projectService = serviceFactory.getProjectService();
	}
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		String page = PAGE_PROJECT_LIST_BY_EMPLOYEE_URI;
		boolean isRedirect = true;

		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);
		String hours = request.getParameter(REQUEST_PARAM_PROJECT_HOURS);

		HttpSession session = request.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute(SESSION_PARAM_USER);
		
		try {
			projectService.updateHours(id, userVO.getEmployee(), hours);
		} catch (ServiceException e) {
			e.printStackTrace();
			request.setAttribute(REQUEST_PARAM_ERROR_MSG, e.getMessage());
			request.setAttribute(REQUEST_PARAM_PROJECT_ID, id);
			request.setAttribute(REQUEST_PARAM_PROJECT_HOURS, hours);
			page = PAGE_PROJECT_UPDATE_HOURS;
			isRedirect = false;			
		}
		
		return new Page(page, isRedirect);
	}
	
}
