package by.htp.devteam.command.user;

import static by.htp.devteam.util.ConstantValue.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.ProjectListDto;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

public class ProjectListAction implements CommandAction{

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);
		
		try {
			ProjectListDto projectDto = projectService.fetchAll(currPage);
			List<Project> projects= projectDto.getProjects();
			System.out.println("PROJECTS LIST=");
			System.out.println(projects);
			
			request.setAttribute(REQUEST_PARAM_PROJECT_LIST, projects);
			request.setAttribute(REQUEST_PARAM_CURR_PAGE, projectDto.getCurrPage());
			request.setAttribute(REQUEST_PARAM_COUNT_PAGES, projectDto.getCountPages());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Page(PAGE_PROJECT_LIST);
	}

}
