package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.controller.Page;

/**
 * Action to show object not found page. Page is used when is not correct forn token from form and from session
 * Logging information about who does action
 * @author julia
 *
 */
public class ObjectNotFoundAction implements CommandAction {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(ObjectNotFoundAction.class.getName());
	
	public ObjectNotFoundAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		logging(request);
		
		return new Page(PAGE_OBJECT_NOT_FOUND);
	}
	
	private void logging(HttpServletRequest request ) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_OBJECT_NOT_FOUND, userVO.getUser().getLogin());
	}

}
