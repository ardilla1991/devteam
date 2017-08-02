package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.controller.Page;

/**
 * Action for show page about result of adding employee.
 * Logging information about who does action
 * @author julia
 *
 */
public final class EmployeeAddMessageAction implements CommandAction{

	public EmployeeAddMessageAction() {
		super();
	}
	
	@Override
	public Page executeGET(HttpServletRequest request, HttpServletResponse response) {

		return new Page(PAGE_EMPLOYEE_ADD_MESSAGE);
	}

	@Override
	public Page executePOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
