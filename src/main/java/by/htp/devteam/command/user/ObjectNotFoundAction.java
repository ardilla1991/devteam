package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	public ObjectNotFoundAction() {
		super();
	}
	
	@Override
	public Page executeGET(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		
		return new Page(PAGE_OBJECT_NOT_FOUND);
	}

	@Override
	public Page executePOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		// TODO Auto-generated method stub
		return null;
	}

}
