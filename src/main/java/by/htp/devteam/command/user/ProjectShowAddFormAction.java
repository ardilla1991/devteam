package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Action to show the form for add project.
 * Logging information about who does action
 * @author julia
 *
 */
public class ProjectShowAddFormAction implements CommandAction{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(ProjectShowAddFormAction.class.getName());
	
	public ProjectShowAddFormAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {	
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		String orderId = request.getParameter(REQUEST_PARAM_ORDER_ID);
		
		logging(request, orderId);
		
		try {
			OrderVo orderVo = orderService.getById(orderId);
			request.setAttribute(REQUEST_PARAM_ORDER_VO, orderVo);
			
			EmployeeService employeeService = serviceFactory.getEmployeeService();
			List<Employee> employees = employeeService.getNotBusyEmployeesForPeriodByQualifications(orderVo.getOrder().getDateStart(), 
					orderVo.getOrder().getDateFinish(), orderVo.getQualifications().keySet());
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_LIST, employees);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}

		request.setAttribute(REQUEST_PARAM_ORDER_ID, orderId);

		return new Page(PAGE_PROJECT_ADD);
	}
	
	private void logging(HttpServletRequest request, String orderId ) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_PROJECT_SWOR_ADD_FORM, userVO.getUser().getLogin(), orderId);
	}

}
