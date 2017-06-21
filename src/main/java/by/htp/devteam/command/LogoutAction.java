package by.htp.devteam.command;

import static by.htp.devteam.util.PageConstantValue.PAGE_LOGIN;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return PAGE_LOGIN;
	}

}
