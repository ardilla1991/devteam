package by.htp.devteam.controller.util;
/**
 * constance values for controller layer. Messages for logger, reguest params, pages paths
 * @author julia
 *
 */
public final class ConstantValue {

	private ConstantValue() {
		super();
	}
	
	public static final String URL_DELIMITER = "/";
	public static final String APP_NAME = "devteam";
	public static final String SERVLET_NAME = "";
	public static final String MODULE = "module";
	public static final String ACTION = "action";
	public static final String URI_START = "?";
	public static final String URI_EQUAL = "=";
	public static final String URI_PARAM_DELIMITER = "&";
	public static final String URL_METHOD_DELIMITER = "_";
	
	public static final String SYSTEM_PATH = URL_DELIMITER + APP_NAME + URL_DELIMITER;

	public static final String MSG_LOGGER_ORDER_ADD_FILE_UPLOAD = "Orders add. Upload file error";

	public static final String MSG_LOGGER_PERMISSION_DENIED = "Permission denied";

	public static final String MSG_LOGGER_NULL_ACTION = "Action is null. Redirect to page with action=empty";
	
	public static final String MSG_COMMAND_EMPTY_MODULE = "Module is empty ";
	public static final String MSG_COMMAND_INVALID_MODULE = "Invalid module: ";
	
	public static final String PAGE_DEFAULT = "index.jsp";
	
	public static final String JSP_FOLDER_PATH = "/WEB-INF/jsp/";
	public static final String JSP_FOLDER_ERROR = JSP_FOLDER_PATH + "error/";
	public static final String PATH_AJAX = "ajax/";
	
	public static final String PAGE_ERROR_500 = JSP_FOLDER_ERROR + "error500.jsp";
	public static final String PAGE_ERROR_404 = JSP_FOLDER_ERROR + "error404.jsp";
	
	public static final String PAGE_LOGIN = JSP_FOLDER_PATH + "login.jsp";
	
	public static final String PATH_ORDER = JSP_FOLDER_PATH + "order/";
	public static final String PAGE_ORDER_LIST = PATH_ORDER + "list.jsp";
	public static final String PAGE_ORDER_ADD = PATH_ORDER + "add.jsp";
	public static final String PAGE_ORDER_VIEW = PATH_ORDER + "view.jsp";
	public static final String PAGE_ORDER_NEW_LIST = PATH_ORDER + "newList.jsp";
	public static final String PAGE_ORDER_ADD_MESSAGE = PATH_ORDER + "addMessage.jsp";
	
	public static final String PATH_PROJECT = JSP_FOLDER_PATH + "project/";
	public static final String PAGE_PROJECT_ADD = PATH_PROJECT + "add.jsp";
	public static final String PAGE_PROJECT_VIEW = PATH_PROJECT + "view.jsp";
	public static final String PAGE_PROJECT_LIST = PATH_PROJECT + "list.jsp";
	public static final String PAGE_PROJECT_ADD_MESSAGE = PATH_PROJECT + "addMessage.jsp";
	public static final String PAGE_PROJECT_UPDATE_HOURS = PATH_PROJECT + "updateHours.jsp";
	public static final String PAGE_PROJECT_FIND = PATH_PROJECT + "find.jsp";
	public static final String PAGE_PROJECT_FIND_AJAX = PATH_PROJECT + PATH_AJAX + "find.jsp";
	
	public static final String PATH_USER = JSP_FOLDER_PATH + "user/";
	public static final String PAGE_USER_VIEW = PATH_USER + "view.jsp";
	public static final String PAGE_USER_LIST = PATH_USER + "list.jsp";
	public static final String PAGE_USER_ADD = PATH_USER + "add.jsp";
	public static final String PAGE_USER_ADD_MESSAGE = PATH_USER + "addMessage.jsp";
	
	public static final String PATH_EMPLOYEE = JSP_FOLDER_PATH + "employee/";
	public static final String PAGE_EMPLOYEE_ADD = PATH_EMPLOYEE + "add.jsp";
	public static final String PAGE_EMPLOYEE_LIST = PATH_EMPLOYEE + "list.jsp";
	public static final String PAGE_EMPLOYEE_ADD_MESSAGE = PATH_EMPLOYEE + "addMessage.jsp";
	
	public static final String PAGE_PERMISSION_DENIED = JSP_FOLDER_PATH + "permissionDenied.jsp";
	public static final String PAGE_OBJECT_NOT_FOUND = JSP_FOLDER_PATH + "objectNotFound.jsp";
	
	public static final String PAGE_PROJECT_LIST_URI = SYSTEM_PATH + "project/list";
	public static final String PAGE_PROJECT_ADD_URI = SYSTEM_PATH + "project/add/order_id/";
	public static final String PAGE_PROJECT_LIST_BY_EMPLOYEE_URI = SYSTEM_PATH + "project/list_by_employee";
	public static final String PAGE_PROJECT_UPDATE_HOURS_URI = SYSTEM_PATH + "project/update_hours/project_id/";
	public static final String PAGE_PROJECT_VIEW_URI = SYSTEM_PATH + "project/view/project_id/";
	public static final String PAGE_PROJECT_ADD_MESSAGE_URI = SYSTEM_PATH + "project/message";
	
	public static final String PAGE_ORDER_LIST_URI = SYSTEM_PATH + "order/list";
	public static final String PAGE_ORDER_NEW_LIST_URI = SYSTEM_PATH + "order/new_list";
	public static final String PAGE_ORDER_ADD_URI = SYSTEM_PATH + "order/add";
	public static final String PAGE_ORDER_ADD_MESSAGE_URI = SYSTEM_PATH + "order/message";
	public static final String PAGE_ORDER_VIEW_URI = SYSTEM_PATH + "order/view/order_id/";
	
	public static final String PAGE_USER_VIEW_URI = SYSTEM_PATH + "user/view";
	public static final String PAGE_USER_LIST_URI = SYSTEM_PATH + "user/list";
	public static final String PAGE_USER_ADD_MESSAGE_URI = SYSTEM_PATH + "user/message";
	public static final String PAGE_USER_ADD_URI = SYSTEM_PATH + "user/add/employee_id/";
	public static final String PAGE_LOGOUT = SYSTEM_PATH + "user/logout";
	
	public static final String PAGE_EMPLOYEE_ADD_URI = SYSTEM_PATH + "employee/add";
	public static final String PAGE_EMPLOYEE_ADD_MESSAGE_URI = SYSTEM_PATH + "employee/message";
	public static final String PAGE_EMPLOYEE_LIST_URI = SYSTEM_PATH + "employee/list";
	
	public static final String PAGE_USER_LOGIN_URI = SYSTEM_PATH + "user/login";
	
	public static final String PAGE_DEFAULT_MANAGER = PAGE_ORDER_NEW_LIST_URI;
	public static final String PAGE_DEFAULT_DEVELOPER = PAGE_PROJECT_LIST_BY_EMPLOYEE_URI;
	public static final String PAGE_DEFAULT_CUSTOMER = PAGE_ORDER_LIST_URI;
	public static final String PAGE_DEFAULT_ADMIN = PAGE_USER_LIST_URI;
	
	public static final String REQUEST_PARAM_MODULE = "module";
	public static final String REQUEST_PARAM_ACTION = "action";
	public static final String REQUEST_PARAM_APP_PATH = "appPath";
	public static final String REQUEST_PARAM_PAGING_VO = "paging_vo";
	
	public static final String REQUEST_PARAM_LOGIN = "login";
	public static final String REQUEST_PARAM_PASSWORD = "pass";
	public static final String REQUEST_PARAM_ORDER_LIST = "order_list";
	public static final String REQUEST_PARAM_PROJECT_LIST = "project_list";
	public static final String REQUEST_PARAM_WORK_LIST = "work_list";
	public static final String REQUEST_PARAM_QUALIFICATION_LIST = "qualification_list";
	public static final String REQUEST_PARAM_UPLOAD_PATH = "upload_path";
	
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
	
	public static final String REQUEST_PARAM_PROJECT_TITLE = "title";
	public static final String REQUEST_PARAM_PROJECT_DESCRIPTION = "description";
	public static final String REQUEST_PARAM_PROJECT_EMPLOYEE = "employee";
	public static final String REQUEST_PARAM_PROJECT_HOURS = "hours";
	
	public static final String REQUEST_PARAM_EMPLOYEE_LIST = "employee_list";
	
	public static final String REQUEST_PARAM_USER_VO = "user_vo";
	public static final String REQUEST_PARAM_USER_LIST = "user_list";
	public static final String REQUEST_PARAM_USER_ROLE_ENUM = "role_enum";
	public static final String REQUEST_PARAM_USER_SHOW_ADD_FORM_URI = "user_add_form_uri";
	public static final String REQUEST_PARAM_USER_LOGIN = "login";
	public static final String REQUEST_PARAM_USER_PASSWORD = "password";
	public static final String REQUEST_PARAM_USER_ROLE = "role";
	
	public static final String REQUEST_PARAM_EMPLOYEE_ID = "employee_id";
	public static final String REQUEST_PARAM_EMPLOYEE_NAME = "name";
	public static final String REQUEST_PARAM_EMPLOYEE_START_WORK = "startWork";
	public static final String REQUEST_PARAM_EMPLOYEE_QUALIFICATION = "qualification";
	
	public static final String JSPF_FOLDER = "fragment/";
	public static final String REQUEST_PARAM_XHR = "xhr";
	public static final String REQUEST_PARAM_JSPF = "jspf";
	
	public static final String PAGE_ORDER_VIEW_FRAGMENT = JSP_FOLDER_PATH + JSPF_FOLDER + "orderView.jsp";
	
	public static final String REQUEST_PARAM_URI = "uri";
	
	public static final String SESSION_PARAM_USER = "user";
}
