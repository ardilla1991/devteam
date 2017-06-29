package by.htp.devteam.util;

public final class ConstantValue {

	private ConstantValue() {
		
	}
	public static final String PAGE_DEFAULT = "index.html";
	
	public static final String JSP_FOLDER_PATH = "/WEB-INF/jsp/";
	
	public static final String PAGE_ERROR = JSP_FOLDER_PATH + "error.jsp";
	
	public static final String PAGE_LOGIN = JSP_FOLDER_PATH + "index.jsp";
	public static final String PAGE_ORDER_LIST = JSP_FOLDER_PATH + "orderList.jsp";
	public static final String PAGE_ORDER_EDIT = JSP_FOLDER_PATH + "orderAddPage.jsp";
	public static final String PAGE_ORDER_VIEW = JSP_FOLDER_PATH + "orderView.jsp";
	public static final String PAGE_PROJECT_EDIT = JSP_FOLDER_PATH + "projectAddPage.jsp";
	public static final String PAGE_PROJECT_VIEW = JSP_FOLDER_PATH + "projectView.jsp";
	public static final String PAGE_PROJECT_LIST = JSP_FOLDER_PATH + "projectList.jsp";
	
	public static final String PAGE_PERMISSION_DENIED = JSP_FOLDER_PATH + "permissionDenied.jsp";
	
	public static final String JSP_FOLDER_AJAX = "ajax/";
	public static final String PAGE_PROJECT_EDIT_AJAX = JSP_FOLDER_PATH + JSP_FOLDER_AJAX + "projectAddPage.jsp";
	
	
	public static final String PAGE_DEFAULT_MANAGER = "Main?action=order_new_list";
	public static final String PAGE_DEFAULT_CUSTOMER = "Main?action=order_list";
	public static final String PAGE_ORDER_NEW_LIST = JSP_FOLDER_PATH + "orderNewList.jsp";

	public static final String REQUEST_PARAM_LOGIN = "login";
	public static final String REQUEST_PARAM_PASSWORD = "pass";
	public static final String REQUEST_PARAM_ORDER_LIST = "order_list";
	public static final String REQUEST_PARAM_PROJECT_LIST = "project_list";
	public static final String REQUEST_PARAM_WORK_LIST = "work_list";
	public static final String REQUEST_PARAM_QUALIFICATION_LIST = "qualification_list";
	public static final String REQUEST_PARAM_ERROR_MSG = "error_message";
	
	public static final String REQUEST_PARAM_PAGE = "page";
	public static final String REQUEST_PARAM_CURR_PAGE = "currPage";
	public static final String REQUEST_PARAM_COUNT_PAGES = "countPages";
	
	public static final String REQUEST_PARAM_ORDER_TITLE = "title";
	public static final String REQUEST_PARAM_ORDER_DESCRIPTION = "description";
	public static final String REQUEST_PARAM_ORDER_SPECIFICATION = "specification";
	public static final String REQUEST_PARAM_ORDER_DATE_START = "dateStart";
	public static final String REQUEST_PARAM_ORDER_DATE_FINISH = "dateFinish";
	public static final String REQUEST_PARAM_ORDER_WORK = "work";
	public static final String REQUEST_PARAM_ORDER_QUALIFICATION = "qualification";
	
	public static final String REQUEST_PARAM_ORDER_ID = "order_id";
	public static final String REQUEST_PARAM_ORDER = "order";
	public static final String REQUEST_PARAM_ORDER_DTO = "order_dto";
	
	public static final String REQUEST_PARAM_PROJECT_TITLE = "title";
	public static final String REQUEST_PARAM_PROJECT_DESCRIPTION = "description";
	public static final String REQUEST_PARAM_PROJECT_EMPLOYEE = "employee";
	
	public static final String REQUEST_PARAM_EMPLOYEE_LIST = "employee_list";
	
	public static final String JSPF_FOLDER = "fragment/";
	public static final String REQUEST_PARAM_XHR = "xhr";
	public static final String REQUEST_PARAM_JSPF = "jspf";
	
	public static final String PAGE_ORDER_VIEW_FRAGMENT = JSP_FOLDER_PATH + JSPF_FOLDER + "orderView.jsp";
	
}
