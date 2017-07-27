package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;

/**
 * Action for show page about result of adding employee.
 * Logging information about who does action
 * @author julia
 *
 */
public final class EmployeeAddMessageAction implements CommandAction{
	
	/** Logger */
	private static final Logger logger = LogManager.getLogger(OrderAddMessageAction.class.getName());

	public EmployeeAddMessageAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_EMPLOYEE_ADD_OK_MESSAGE, userVO.getUser().getLogin());
		
		return new Page(PAGE_EMPLOYEE_ADD_MESSAGE);
	}
	
	
}
