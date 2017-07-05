package by.htp.devteam.util;

public final class ConstantValue {

	private ConstantValue() {
		
	}
	public static final String PAGE_DEFAULT = "index.html";
	
	public static final String JSP_FOLDER_PATH = "/WEB-INF/jsp/";
	
	public static final String PAGE_ERROR = JSP_FOLDER_PATH + "error.jsp";
	public static final String PAGE_LOGIN = JSP_FOLDER_PATH + "login.jsp";
	
	public static final String PATH_ORDER = JSP_FOLDER_PATH + "order/";
	public static final String PAGE_ORDER_LIST = PATH_ORDER + "list.jsp";
	public static final String PAGE_ORDER_EDIT = PATH_ORDER + "add.jsp";
	public static final String PAGE_ORDER_VIEW = PATH_ORDER + "view.jsp";
	public static final String PAGE_ORDER_NEW_LIST = PATH_ORDER + "newList.jsp";
	
	public static final String PATH_PROJECT = JSP_FOLDER_PATH + "project/";
	public static final String PAGE_PROJECT_EDIT = PATH_PROJECT + "add.jsp";
	public static final String PAGE_PROJECT_VIEW = PATH_PROJECT + "view.jsp";
	public static final String PAGE_PROJECT_LIST = PATH_PROJECT + "list.jsp";
	
	public static final String PAGE_PERMISSION_DENIED = JSP_FOLDER_PATH + "permissionDenied.jsp";
	
	public static final String JSP_FOLDER_AJAX = "ajax/";
	public static final String PAGE_PROJECT_EDIT_AJAX = JSP_FOLDER_PATH + JSP_FOLDER_AJAX + "projectAddPage.jsp";
	
	public static final String PAGE_PROJECT_LIST_URI = "Main?action=project_list";
	public static final String PAGE_PROJECT_SHOW_ADD_FORM = "Main?action=project_show_add_form&order_id=";
	public static final String PAGE_PROJECT_LIST_BY_EMPLOYEE_URI = "Main?action=project_list_by_employee";
	public static final String PAGE_PROJECT_UPDATE_HOURS = "Main?action=project_update_hours";
	
	public static final String PAGE_ORDER_LIST_URI = "Main?action=order_list";
	public static final String PAGE_ORDER_NEW_LIST_URI = "Main?action=order_new_list";
	public static final String PAGE_ORDER_SHOW_ADD_FORM_URI = "Main?action=order_show_add_form";
	
	public static final String PAGE_DEFAULT_MANAGER = PAGE_ORDER_NEW_LIST_URI;
	public static final String PAGE_DEFAULT_DEVELOPER = PAGE_PROJECT_LIST_BY_EMPLOYEE_URI;
	public static final String PAGE_DEFAULT_CUSTOMER = PAGE_ORDER_LIST_URI;
	
	public static final String REQUEST_PARAM_ACTION = "action";
	
	public static final String REQUEST_PARAM_LOGIN = "login";
	public static final String REQUEST_PARAM_PASSWORD = "pass";
	public static final String REQUEST_PARAM_ORDER_LIST = "order_list";
	public static final String REQUEST_PARAM_PROJECT_LIST = "project_list";
	public static final String REQUEST_PARAM_WORK_LIST = "work_list";
	public static final String REQUEST_PARAM_QUALIFICATION_LIST = "qualification_list";
	
	
	public static final String REQUEST_PARAM_ERROR_MSG = "error_message";
	public static final String REQUEST_PARAM_ERROR_CODE = "error_code";
	public static final String REQUEST_PARAM_ERROR_FIELD = "empty_field";
	
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
	public static final String REQUEST_PARAM_ORDER_PRICE = "price";
	
	public static final String REQUEST_PARAM_ORDER_ID = "order_id";
	public static final String REQUEST_PARAM_ORDER = "order";
	public static final String REQUEST_PARAM_ORDER_VO = "order_vo";
	
	public static final String REQUEST_PARAM_PROJECT_ID = "project_id";
	public static final String REQUEST_PARAM_PROJECT_VO = "project_vo";
	public static final String REQUEST_PARAM_PROJECT_LIST_VO = "project_list_vo";
	
	public static final String REQUEST_PARAM_PROJECT_TITLE = "title";
	public static final String REQUEST_PARAM_PROJECT_DESCRIPTION = "description";
	public static final String REQUEST_PARAM_PROJECT_EMPLOYEE = "employee";
	public static final String REQUEST_PARAM_PROJECT_HOURS = "hours";
	
	public static final String REQUEST_PARAM_EMPLOYEE_LIST = "employee_list";
	
	public static final String JSPF_FOLDER = "fragment/";
	public static final String REQUEST_PARAM_XHR = "xhr";
	public static final String REQUEST_PARAM_JSPF = "jspf";
	
	public static final String PAGE_ORDER_VIEW_FRAGMENT = JSP_FOLDER_PATH + JSPF_FOLDER + "orderView.jsp";
	
	public static final String REQUEST_PARAM_URI = "uri";
	
	public static final String SESSION_PARAM_USER = "user";
	
}
