package by.htp.devteam.controller.module.impl;

import static by.htp.devteam.controller.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.main.Page;
import by.htp.devteam.controller.module.EmployeeController;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.QualificationService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

public final class EmployeeControllerImpl implements EmployeeController {
	
	public EmployeeControllerImpl() {
		super();
	}
	
	@Override
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		
		String page = PAGE_EMPLOYEE_ADD_MESSAGE_URI;
		String name = request.getParameter(REQUEST_PARAM_EMPLOYEE_NAME);
		String startWork = request.getParameter(REQUEST_PARAM_EMPLOYEE_START_WORK);
		String qualification = request.getParameter(REQUEST_PARAM_EMPLOYEE_QUALIFICATION);
		
		try {
			employeeService.add(name, startWork, qualification);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_NAME, name);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_START_WORK, startWork);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_QUALIFICATION, qualification);
			
			return addGET(request, response);
		}
		
		return new Page(page, true);
	}

	@Override
	public Page addGET(HttpServletRequest request, HttpServletResponse response) {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		QualificationService qualificationService = serviceFactory.getQualificationService();
		try {
			request.setAttribute(REQUEST_PARAM_QUALIFICATION_LIST, qualificationService.fetchAll());
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_EMPLOYEE_ADD);
	}
	
	@Override
	public Page messageGET(HttpServletRequest request, HttpServletResponse response) {

		return new Page(PAGE_EMPLOYEE_ADD_MESSAGE);
	}
	
	@Override
	public Page listGET(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);
		try {
			PagingVo<Employee> pagingVo = employeeService.fetchAll(currPage);
			pagingVo.setUri(PAGE_EMPLOYEE_LIST_URI);
			request.setAttribute(REQUEST_PARAM_PAGING_VO, pagingVo);
			
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_LIST, pagingVo.getRecords());
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_EMPLOYEE_LIST);
	}
}
