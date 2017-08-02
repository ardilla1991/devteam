package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.controller.Page;

import static by.htp.devteam.command.util.ConstantValue.*;

/**
 * Action to display user information.
 * Logging information about who does action
 * @author julia
 *
 */
public class UserViewAction implements CommandAction{
	
	public UserViewAction() {
		super();
	}
	
	@Override
	public Page executeGET(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		request.setAttribute(REQUEST_PARAM_USER_VO, userVO);
		
		return new Page(PAGE_USER_VIEW);
	}

	@Override
	public Page executePOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		// TODO Auto-generated method stub
		return null;
	}

}
