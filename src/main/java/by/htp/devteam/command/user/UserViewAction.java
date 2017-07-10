package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static by.htp.devteam.command.util.ConstantValue.*;

public class UserViewAction implements CommandAction{

	private static final Logger logger = LogManager.getLogger(UserViewAction.class.getName());
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute(SESSION_PARAM_USER);
		
		request.setAttribute(REQUEST_PARAM_USER_VO, userVO);
		
		logger.info(MSG_LOGGER_USER_VIEW, userVO.getUser().getLogin());
		
		return new Page(PAGE_USER_VIEW);
	}

}
