package by.htp.devteam.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.controller.Page;

public interface CommandAction {
	
	public Page execute(HttpServletRequest request, HttpServletResponse response);
}
