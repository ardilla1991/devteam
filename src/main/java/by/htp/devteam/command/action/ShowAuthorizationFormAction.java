package by.htp.devteam.command.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import by.htp.devteam.command.CommandAction;

import static by.htp.devteam.util.PageConstantValue.*;

public class ShowAuthorizationFormAction implements CommandAction{
	

	 //private static final Logger logger = LogManager.getLogger(ShowAuthorizationFormAction.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//logger.info("dd");
		return PAGE_LOGIN;
	}

}
