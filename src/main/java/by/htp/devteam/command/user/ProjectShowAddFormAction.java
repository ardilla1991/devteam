package by.htp.devteam.command.user;

import static by.htp.devteam.util.ConstantValue.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.service.QualificationService;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.WorkService;

public class ProjectShowAddFormAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		/*ServiceFactory serviceFactory = ServiceFactory.getInstance();
		
		WorkService workService = serviceFactory.getWorkService();
		List<Work> works = workService.fetchAll();
		request.setAttribute(REQUEST_PARAM_WORK_LIST, works);
		
		QualificationService qualificationService = serviceFactory.getQualificationService();
		List<Qualification> qualifications = qualificationService.fetchAll();
		request.setAttribute(REQUEST_PARAM_QUALIFICATION_LIST, qualifications);*/
		String orderId = request.getParameter(REQUEST_PARAM_ORDER_ID);
		request.setAttribute(REQUEST_PARAM_ORDER_ID, Integer.valueOf(orderId));
		
		String xhr = request.getParameter(REQUEST_PARAM_XHR);
		System.out.println("xhr="+xhr);
		String page = ( xhr != null ? PAGE_PROJECT_EDIT_AJAX : PAGE_PROJECT_EDIT );
		System.out.println("page="+page);
		return page;
	}

}
