package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		QualificationService qualificationService = serviceFactory.getQualificationService();
		try {
			request.setAttribute(REQUEST_PARAM_WORK_LIST, workService.fetchAll());
			request.setAttribute(REQUEST_PARAM_QUALIFICATION_LIST, qualificationService.fetchAll());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		
		return new Page(PAGE_ORDER_EDIT);
	}

}
