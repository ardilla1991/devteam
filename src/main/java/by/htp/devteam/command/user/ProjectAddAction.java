package by.htp.devteam.command.user;

import static by.htp.devteam.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

public class ProjectAddAction implements CommandAction{
	
	private ProjectService projectService;
	private OrderService orderService;
	private static final Logger logger = LogManager.getLogger(LoginAction.class.getName());
	
	public ProjectAddAction() {
		super();
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		projectService = serviceFactory.getProjectService();
		orderService = serviceFactory.getOrderService();
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		/*List<String> parameterNames = new ArrayList<String>(request.getParameterMap().values());
		for ( String st : parameterNames ) {
			System.out.println(st);
		}*/
		String page = "Main?action=project_list";
		boolean isRedirect = true;
		String orderId = request.getParameter(REQUEST_PARAM_ORDER_ID);
		OrderDto order = orderService.getOrderById(orderId);
		
		String title = request.getParameter(REQUEST_PARAM_PROJECT_TITLE);
		String description = request.getParameter(REQUEST_PARAM_PROJECT_DESCRIPTION);
		String[] employees = request.getParameterValues(REQUEST_PARAM_PROJECT_EMPLOYEE);
		
		Project project = null;
		try {
			project = projectService.add(order, title, description, employees);
		} catch (ServiceException e) {
			logger.info("fill the title and description");
			e.printStackTrace();
			request.setAttribute(REQUEST_PARAM_ERROR_MSG, e.getMessage());
			request.setAttribute(REQUEST_PARAM_PROJECT_TITLE, title);
			request.setAttribute(REQUEST_PARAM_PROJECT_DESCRIPTION, description);
			page = "Main?action=project_show_add_form&order_id=" + orderId;
			isRedirect = false;			
		}
		
		return new Page(page, isRedirect);
	}
	
}
