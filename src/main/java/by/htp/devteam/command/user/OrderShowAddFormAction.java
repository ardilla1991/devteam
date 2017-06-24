package by.htp.devteam.command.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.service.QualificationService;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.WorkService;
import by.htp.devteam.util.PageConstantValue;
import by.htp.devteam.util.RequestParamConstantValue;

public class OrderShowAddFormAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		
		WorkService workService = serviceFactory.getWorkService();
		List<Work> works = workService.fetchAll();
		request.setAttribute(RequestParamConstantValue.WORK_LIST, works);
		
		QualificationService qualificationService = serviceFactory.getQualificationService();
		List<Qualification> qualifications = qualificationService.fetchAll();
		request.setAttribute(RequestParamConstantValue.QUALIFICATION_LIST, qualifications);
		
		return PageConstantValue.ORDER_EDIT_PAGE;
	}

}
