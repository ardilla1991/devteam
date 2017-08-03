package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.util.CSRFToken;
import by.htp.devteam.controller.Page;

import static by.htp.devteam.command.util.ConstantValue.*;

/**
 * Action to show auorization form.
 * Logging information about who does action
 * @author julia
 *
 */
public class ShowAuthorizationFormAction implements CommandAction {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(ShowAuthorizationFormAction.class);
	
	public ShowAuthorizationFormAction() {
		super();
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {

		String page = PAGE_LOGIN;
		boolean isRedirect = false;
		HttpSession session = request.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute(SESSION_PARAM_USER) != null;
		if ( isAuthorised ) {
			Object userObject = session.getAttribute(SESSION_PARAM_USER);
			UserVo userVO = (UserVo) userObject;
			isRedirect = true;
			switch (userVO.getUser().getRole()) {
			case MANAGER:
				page = PAGE_DEFAULT_MANAGER;
				break;
			case CUSTOMER:
				page = PAGE_DEFAULT_CUSTOMER;
				break;
			case DEVELOPER:
				page = PAGE_DEFAULT_DEVELOPER;
				break;
			case ADMIN:
				page = PAGE_DEFAULT_ADMIN;
				break;
			default:
				break;
			}
			
			logger.info(MSG_LOGGER_SHOW_AUTHORIZATION_FORM, userVO.getUser().getLogin());
		} else {
			logger.info(MSG_LOGGER_SHOW_AUTHORIZATION_FORM);
		}

		return new Page(page, isRedirect);
	}

}
