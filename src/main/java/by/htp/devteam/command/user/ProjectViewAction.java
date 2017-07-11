package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.vo.ProjectVo;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ProjectViewAction implements CommandAction{
	
	private static final Logger logger = LogManager.getLogger(ProjectViewAction.class.getName());

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		String page = PAGE_PROJECT_VIEW;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);
		
		logging(request, id);
		
		try {
			ProjectVo projectVo = projectService.getById(id);
			request.setAttribute(REQUEST_PARAM_PROJECT_VO, projectVo);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			page = PAGE_ERROR_404;
		}
		
		return new Page(page);
	}
	
	private void logging(HttpServletRequest request, String id ) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_PROJECT_VIEW, userVO.getUser().getLogin(), id);
	}
	
}
