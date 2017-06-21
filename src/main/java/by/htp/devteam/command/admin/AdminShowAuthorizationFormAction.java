package by.htp.devteam.command.admin;

import javax.servlet.http.HttpServletRequest;
import static by.htp.devteam.util.admin.AdminPageConstantValue.*;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.command.CommandAction;

public class AdminShowAuthorizationFormAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		return PAGE_LOGIN;
	}
	
}
