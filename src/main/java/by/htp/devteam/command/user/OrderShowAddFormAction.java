package by.htp.devteam.command.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.QualificationService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.WorkService;
import static by.htp.devteam.util.ConstantValue.*;

public class OrderShowAddFormAction implements CommandAction{

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		
		WorkService workService = serviceFactory.getWorkService();
		List<Work> works = workService.fetchAll();
		request.setAttribute(REQUEST_PARAM_WORK_LIST, works);
		
		QualificationService qualificationService = serviceFactory.getQualificationService();
		List<Qualification> qualifications;
		try {
			qualifications = qualificationService.fetchAll();
			request.setAttribute(REQUEST_PARAM_QUALIFICATION_LIST, qualifications);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		
		return new Page(PAGE_ORDER_EDIT);
	}

}
