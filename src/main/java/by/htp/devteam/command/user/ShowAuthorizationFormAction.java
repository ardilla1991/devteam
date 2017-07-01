package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import by.htp.devteam.bean.RoleEnum;
import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;

import static by.htp.devteam.util.ConstantValue.*;

public class ShowAuthorizationFormAction implements CommandAction{
	

	 //private static final Logger logger = LogManager.getLogger(ShowAuthorizationFormAction.class);
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		
		String page = PAGE_LOGIN;;
		boolean isNeededRedirect = false;
		HttpSession session = request.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute("user") != null;
		if ( isAuthorised ) {
			Object userObject = session.getAttribute("user");
			UserVO userVO = (UserVO) userObject;
			isNeededRedirect = true;
			if ( userVO.getUser().getRole() == RoleEnum.MANAGER ) {
				page = PAGE_DEFAULT_MANAGER;
			} else if ( userVO.getUser().getRole() == RoleEnum.CUSTOMER ){
				page = PAGE_DEFAULT_CUSTOMER;
			} else if ( userVO.getUser().getRole() == RoleEnum.DEVELOPER ){ 
				page = PAGE_DEFAULT_DEVELOPER;
			}
		}
			
		return new Page(page, isNeededRedirect);
	}

}
