package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class PermissionDeniedAction implements CommandAction{

	private static final Logger logger = LogManager.getLogger(PermissionDeniedAction.class.getName());
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		
		logging(request);
		
		return new Page(PAGE_PERMISSION_DENIED);
	}
	
	private void logging(HttpServletRequest request ) {
		HttpSession session = request.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_PERMISSION_DENIED, userVO.getUser().getLogin());
	}

}
