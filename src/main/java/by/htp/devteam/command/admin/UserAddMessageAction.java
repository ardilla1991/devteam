package by.htp.devteam.command.admin;

import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.controller.Page;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserAddMessageAction implements CommandAction{
	
	public UserAddMessageAction() {
		super();
	}
	
	@Override
	public Page executeGET(HttpServletRequest request, HttpServletResponse response) {
		
		return new Page(PAGE_USER_ADD_MESSAGE);
	}

	@Override
	public Page executePOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		// TODO Auto-generated method stub
		return null;
	}

}
