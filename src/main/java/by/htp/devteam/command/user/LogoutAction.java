package by.htp.devteam.command.user;

import static by.htp.devteam.util.ConstantValue.PAGE_LOGIN;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.command.CommandAction;

public class LogoutAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return PAGE_LOGIN;
	}

}
