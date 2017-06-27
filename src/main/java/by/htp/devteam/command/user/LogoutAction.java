package by.htp.devteam.command.user;

import static by.htp.devteam.util.ConstantValue.PAGE_LOGIN;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;

public class LogoutAction implements CommandAction{

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return new Page(PAGE_LOGIN);
	}

}
