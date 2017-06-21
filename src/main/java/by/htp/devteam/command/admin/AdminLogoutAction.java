package by.htp.devteam.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.htp.devteam.util.admin.AdminPageConstantValue.*;

import by.htp.devteam.command.CommandAction;

public class AdminLogoutAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return PAGE_LOGIN;
	}

}
