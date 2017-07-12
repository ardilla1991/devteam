package by.htp.devteam.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.vo.UserListVo;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.UserService;

import static by.htp.devteam.command.util.ConstantValue.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Action for getting all users. 
 * Logging information about who does action
 * @author julia
 *
 */
public final class UserListAction implements CommandAction{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(UserListAction.class.getName());
	
	public UserListAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_USER_LIST, userVO.getUser().getLogin());
		
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);
		try {
			UserListVo userListVo = userService.fetchAll(currPage);
			
			request.setAttribute(REQUEST_PARAM_URI, PAGE_USER_LIST_URI);
			request.setAttribute(REQUEST_PARAM_USER_LIST_VO, userListVo);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_USER_LIST);
	}
	
}
