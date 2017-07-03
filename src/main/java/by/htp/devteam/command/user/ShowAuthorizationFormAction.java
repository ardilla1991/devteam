package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;

import static by.htp.devteam.util.ConstantValue.*;

public class ShowAuthorizationFormAction implements CommandAction {

	// private static final Logger logger =
	// LogManager.getLogger(ShowAuthorizationFormAction.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {

		String page = PAGE_LOGIN;
		boolean isRedirect = false;
		HttpSession session = request.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute(SESSION_PARAM_USER) != null;
		if (isAuthorised) {
			Object userObject = session.getAttribute(SESSION_PARAM_USER);
			UserVO userVO = (UserVO) userObject;
			isRedirect = true;
			switch ( userVO.getUser().getRole() ) {
				case MANAGER:
					page = PAGE_DEFAULT_MANAGER;
					break;
				case CUSTOMER:
					page = PAGE_DEFAULT_CUSTOMER;
					break;
				case DEVELOPER:
					page = PAGE_DEFAULT_DEVELOPER;
					break;
				default:
					break;
			}
		}

		return new Page(page, isRedirect);
	}

}
