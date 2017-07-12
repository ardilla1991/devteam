package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;

import static by.htp.devteam.command.util.ConstantValue.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Action after added order. Only show page with message that all is ok
 * Logging information about who does action
 * @author julia
 *
 */
public class OrderAddMessageAction implements CommandAction{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(OrderAddMessageAction.class.getName());
	
	public OrderAddMessageAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_ORDER_ADD_OK_MESSAGE, userVO.getUser().getLogin());
		
		return new Page(PAGE_ORDER_ADD_MESSAGE);
	}

}
