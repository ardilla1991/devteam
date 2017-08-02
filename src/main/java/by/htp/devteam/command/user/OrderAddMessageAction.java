package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.controller.Page;

import static by.htp.devteam.command.util.ConstantValue.*;

/**
 * Action after added order. Only show page with message that all is ok
 * Logging information about who does action
 * @author julia
 *
 */
public class OrderAddMessageAction implements CommandAction{
	
	public OrderAddMessageAction() {
		super();
	}
	
	@Override
	public Page executeGET(HttpServletRequest request, HttpServletResponse response) {
		
		return new Page(PAGE_ORDER_ADD_MESSAGE);
	}

	@Override
	public Page executePOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		// TODO Auto-generated method stub
		return null;
	}

}
