package by.htp.devteam.command.action.admin;

import static by.htp.devteam.util.admin.AdminPageConstantValue.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.RoleEnum;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.util.admin.AdminRequestParamConstantValue;


public class AdminLoginAction implements CommandAction{

	//private static final Logger logger = LogManager.getLogger(AdminLoginAction.class.getName());
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//logger.info("Hello, World!");
		HttpSession session = request.getSession();
		String login = request.getParameter(AdminRequestParamConstantValue.LOGIN);
		String password = request.getParameter(AdminRequestParamConstantValue.PASSWORD);
		String page = PAGE_DEFAULT;
		
		User employee;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		try {
			employee = employeeService.authorise(login, password);
			
			session.setAttribute("user", employee);
			String currPage = request.getParameter(AdminRequestParamConstantValue.PAGE);
			if ( employee.getRole() == RoleEnum.MANAGER ) { // manager
				//display list of projects
				
				ProjectService projectService = serviceFactory.getProjectService();
				ProjectDto projectDto = projectService.getNewProjects(currPage);
				List<Project> projects = projectDto.getProjects();
				
				request.setAttribute(AdminRequestParamConstantValue.PROJECT_LIST, projects);
				request.setAttribute(AdminRequestParamConstantValue.CURR_PAGE, projectDto.getCurrPage());
				request.setAttribute(AdminRequestParamConstantValue.COUNT_PAGES, projectDto.getCountPages());

				page = PROJECT_NEW_LIST;
			} else if ( employee.getRole() == RoleEnum.DEVELOPER ){  // developer
				//display list of projects for curr employee
				
				ProjectService projectService = serviceFactory.getProjectService();
				ProjectDto projectDto = projectService.getNewProjects(currPage);
				List<Project> projects = projectDto.getProjects();

				request.setAttribute(AdminRequestParamConstantValue.PROJECT_LIST, projects);
				request.setAttribute(AdminRequestParamConstantValue.CURR_PAGE, projectDto.getCurrPage());
				request.setAttribute(AdminRequestParamConstantValue.COUNT_PAGES, projectDto.getCountPages());

				page = PROJECT_NEW_LIST;
				response.sendRedirect(page);
				return null;
			}
		} catch (ServiceException | IOException e1) { 
			//e1.printStackTrace();
			//page = PAGE_ERROR;
			System.out.println(e1.getMessage());
			request.setAttribute(AdminRequestParamConstantValue.ERROR_MSG, e1.getMessage());
			page = PAGE_LOGIN;
		} 
		
		return page;
	}

}
