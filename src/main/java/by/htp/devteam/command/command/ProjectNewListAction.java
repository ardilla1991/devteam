package by.htp.devteam.command.command;

import static by.htp.devteam.util.PageConctantValue.PAGE_DEFAULT;
import static by.htp.devteam.util.PageConctantValue.PROJECT_NEW_LIST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Project;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.util.RequestParamConstantValue;

public class ProjectNewListAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String currPage = request.getParameter(RequestParamConstantValue.PAGE);
		String page = PAGE_DEFAULT;
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		
		ProjectService projectService = serviceFactory.getProjectService();
		List<Project> projects = projectService.getNewProjects(currPage);
		request.setAttribute(RequestParamConstantValue.PROJECT_LIST, projects);
		request.setAttribute(RequestParamConstantValue.CURR_PAGE, currPage);
		System.out.println("djdjdj");
		page = PROJECT_NEW_LIST;
		
		return page;
	}

	
}
