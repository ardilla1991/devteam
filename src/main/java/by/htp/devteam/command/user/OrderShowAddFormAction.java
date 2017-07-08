package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.QualificationService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.WorkService;
import static by.htp.devteam.command.util.ConstantValue.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class OrderShowAddFormAction implements CommandAction{
	
	private static final Logger logger = LogManager.getLogger(OrderShowAddFormAction.class.getName());

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		logging(request);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		
		WorkService workService = serviceFactory.getWorkService();
		QualificationService qualificationService = serviceFactory.getQualificationService();
		try {
			request.setAttribute(REQUEST_PARAM_WORK_LIST, workService.fetchAll());
			request.setAttribute(REQUEST_PARAM_QUALIFICATION_LIST, qualificationService.fetchAll());
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_ORDER_EDIT);
	}
	
	private void logging(HttpServletRequest request ) {
		HttpSession session = request.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_ORDER_SWOR_ADD_FORM, userVO.getUser().getLogin());
	}

}
