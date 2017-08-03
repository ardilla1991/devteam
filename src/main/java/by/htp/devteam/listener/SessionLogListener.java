package by.htp.devteam.listener;

import static by.htp.devteam.module.util.ConstantValue.MSG_LOGGER_REQUEST;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionLogListener implements HttpSessionListener  {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(SessionLogListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
        
        logger.info("Session with id = " + session.getId() + " is started");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		 HttpSession session = se.getSession();
		 logger.info("Session with id = " + session.getId() + " is finished");
	}

}
