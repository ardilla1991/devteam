package by.htp.devteam.command.action.admin;

import static by.htp.devteam.util.admin.AdminPageConstantValue.PAGE_PERMISSION_DENIED;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.command.CommandAction;

public class AdminPermissionDeniedAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_PERMISSION_DENIED;
	}

}
