package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LogoutAction implements CommandAction{

	private static final Logger logger = LogManager.getLogger(LogoutAction.class.getName());
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		if ( userVO != null ) {			
			logger.info(MSG_LOGGER_USER_LOGOUT, userVO.getUser().getLogin());
			
			request.getSession().removeAttribute(SESSION_PARAM_USER);
			request.getSession().invalidate();
		} else {
			logger.info(MSG_LOGGER_USER_LOGOUT);
		}
		
		return new Page(PAGE_SHOW_AUTHORIZATION_FORM_URI, true);
	}

}
