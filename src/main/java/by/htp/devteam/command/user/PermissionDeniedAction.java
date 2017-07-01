package by.htp.devteam.command.user;

import static by.htp.devteam.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;

public class PermissionDeniedAction implements CommandAction{

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		return new Page(PAGE_PERMISSION_DENIED);
	}

}