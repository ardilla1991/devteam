package by.htp.devteam.command.command;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.util.RequestParamConstantValue;

import static by.htp.devteam.util.PageConctantValue.*;

public class LoginCommandAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String login = request.getParameter(RequestParamConstantValue.LOGIN);
		String password = request.getParameter(RequestParamConstantValue.PASSWORD);
		String page = PAGE_DEFAULT;
		
		Employee employee;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		try {
			employee = employeeService.authorise(login, password);
			//orderService.prepareBase(equipService);
			if ( employee.getRole() == 2 ) { // manager
				//display list of projects
				
				ProjectService projectService = serviceFactory.getProjectService();
				List<Project> projects = projectService.getNewProjects("1");
				request.setAttribute(RequestParamConstantValue.PROJECT_LIST, projects);
				System.out.println("djdjdj");
				page = PROJECT_NEW_LIST;
			} else if ( employee.getRole() == 3 ){  // developer
				//display list of projects for curr employee
				
				ProjectService projectService = serviceFactory.getProjectService();
				List<Project> projects = projectService.getNewProjects("1");
				//System.out.println(equipment);

				request.setAttribute(RequestParamConstantValue.CURR_PAGE, "1");
				request.setAttribute(RequestParamConstantValue.PROJECT_LIST, projects);

				page = PROJECT_NEW_LIST;
			}
		} catch (ServiceException e1) {
			//e1.printStackTrace();
			page = PAGE_ERROR;
			request.setAttribute(RequestParamConstantValue.ERROR_MSG, e1.getMessage());
		}	
		
		return page;
	}

}
