package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.dto.ProjectListVo;
import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ProjectListByEmployeeAction implements CommandAction{

	private static final Logger logger = LogManager.getLogger(ProjectListByEmployeeAction.class.getName());
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);
		
		HttpSession session = request.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_PROJECT_LIST_BY_EMPLOYEE, userVO.getUser().getLogin(), currPage);
		
		try {
			ProjectListVo projectListVo = projectService.fetchAll(currPage, userVO.getEmployee());

			request.setAttribute(REQUEST_PARAM_URI, PAGE_DEFAULT_DEVELOPER);
			request.setAttribute(REQUEST_PARAM_PROJECT_LIST_VO, projectListVo);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_PROJECT_LIST);
	}

}
