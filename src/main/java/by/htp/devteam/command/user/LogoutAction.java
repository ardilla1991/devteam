package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.controller.Page;

/**
 * Action for user's logout.
 * Logging information about who does action
 * @author julia
 *
 */
public final class LogoutAction implements CommandAction{
	
	public LogoutAction() {
		super();
	}
	
	@Override
	public Page executeGET(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		if ( userVO != null ) {
			request.getSession().removeAttribute(SESSION_PARAM_USER);
			request.getSession().invalidate();
		}
		
		return new Page(PAGE_SHOW_AUTHORIZATION_FORM_URI, true);
	}

	@Override
	public Page executePOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		// TODO Auto-generated method stub
		return null;
	}

}
