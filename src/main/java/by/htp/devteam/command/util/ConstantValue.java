package by.htp.devteam.command.util;

public final class ConstantValue {

	private ConstantValue() {
		super();
	}
	
	public static final String MSG_LOGGER_USER_LOGIN = "User {}. Login action";
	public static final String MSG_LOGGER_USER_LOGOUT = "User {}. Logout action";
	public static final String MSG_LOGGER_ORDER_NEW_LIST = "User {}. New orders list";
	public static final String MSG_LOGGER_ORDER_SWOR_ADD_FORM = "User {}. Show order add form";
	public static final String MSG_LOGGER_ORDER_LIST = "User {}. Orders list";
	public static final String MSG_LOGGER_ORDER_ADD = "User {}. Orders add";
	public static final String MSG_LOGGER_ORDER_ADD_OK_MESSAGE = "User {}. Add order --> OK";
	public static final String MSG_LOGGER_ORDER_ADD_FILE_UPLOAD = "Orders add. Upload file error";
	public static final String MSG_LOGGER_ORDER_VIEW = "User {}. Order id {} view";
	public static final String MSG_LOGGER_PERMISSION_DENIED = "User {}. Permission denied";
	public static final String MSG_LOGGER_PROJECT_ADD = "User {}. Add project for order {}";
	public static final String MSG_LOGGER_PROJECT_ADD_OK_MESSAGE = "User {}. Add project --> OK";
	public static final String MSG_LOGGER_PROJECT_LIST = "User {}. Projects list";
	public static final String MSG_LOGGER_PROJECT_LIST_BY_EMPLOYEE = "User {}. Projects list by employee. Page {}";
	public static final String MSG_LOGGER_PROJECT_FIND = "User {}. Find project by name {}";
	public static final String MSG_LOGGER_PROJECT_SWOR_ADD_FORM = "User {}. Show project add form for order {}";
	public static final String MSG_LOGGER_PROJECT_UPDATE_HOURS = "User {}. Project {} . Update hours";
	public static final String MSG_LOGGER_PROJECT_VIEW = "User {}. Project {} view";
	public static final String MSG_LOGGER_SHOW_AUTHORIZATION_FORM = "User {}. Show authorization form";
	public static final String MSG_LOGGER_USER_VIEW = "User {}. View user's information";
	
	public static final String MSG_LOGGER_USER_LIST = "User {}. User's list";
	
	public static final String MSG_LOGGER_NULL_ACTION = "Action is null. Redirect to page with action=empty";
	public static final String MSG_LOGGER_WRONG_HTTP_METHOD = "User {}. Wrong http method ";
	
	public static final String PAGE_DEFAULT = "index.html";
	
	public static final String JSP_FOLDER_PATH = "/WEB-INF/jsp/";
	public static final String JSP_FOLDER_ERROR = JSP_FOLDER_PATH + "error/";
	public static final String PATH_AJAX = "ajax/";
	
	public static final String PAGE_ERROR = JSP_FOLDER_PATH + "error.jsp";
	public static final String PAGE_ERROR_500 = JSP_FOLDER_ERROR + "error500.jsp";
	public static final String PAGE_ERROR_404 = JSP_FOLDER_ERROR + "error404.jsp";
	
	public static final String PAGE_LOGIN = JSP_FOLDER_PATH + "login.jsp";
	
	public static final String PATH_ORDER = JSP_FOLDER_PATH + "order/";
	public static final String PAGE_ORDER_LIST = PATH_ORDER + "list.jsp";
	public static final String PAGE_ORDER_EDIT = PATH_ORDER + "add.jsp";
	public static final String PAGE_ORDER_VIEW = PATH_ORDER + "view.jsp";
	public static final String PAGE_ORDER_NEW_LIST = PATH_ORDER + "newList.jsp";
	public static final String PAGE_ORDER_ADD_MESSAGE = PATH_ORDER + "addMessage.jsp";
	
	public static final String PATH_PROJECT = JSP_FOLDER_PATH + "project/";
	public static final String PAGE_PROJECT_EDIT = PATH_PROJECT + "add.jsp";
	public static final String PAGE_PROJECT_VIEW = PATH_PROJECT + "view.jsp";
	public static final String PAGE_PROJECT_LIST = PATH_PROJECT + "list.jsp";
	public static final String PAGE_PROJECT_ADD_MESSAGE = PATH_PROJECT + "addMessage.jsp";
	public static final String PAGE_PROJECT_FIND = PATH_PROJECT + "find.jsp";
	public static final String PAGE_PROJECT_FIND_AJAX = PATH_PROJECT + PATH_AJAX + "find.jsp";
	
	public static final String PATH_USER = JSP_FOLDER_PATH + "user/";
	public static final String PAGE_USER_VIEW = PATH_USER + "view.jsp";
	public static final String PAGE_USER_LIST = PATH_USER + "list.jsp";
	
	public static final String PAGE_PERMISSION_DENIED = JSP_FOLDER_PATH + "permissionDenied.jsp";
	
	public static final String PAGE_PROJECT_LIST_URI = "Main?action=project_list";
	public static final String PAGE_PROJECT_SHOW_ADD_FORM_URI = "Main?action=project_show_add_form&order_id=";
	public static final String PAGE_PROJECT_LIST_BY_EMPLOYEE_URI = "Main?action=project_list_by_employee";
	public static final String PAGE_PROJECT_UPDATE_HOURS_URI = "Main?action=project_update_hours";
	public static final String PAGE_PROJECT_VIEW_BY_ID_URI = "Main?action=project_view&project_id=";
	public static final String PAGE_PROJECT_ADD_MESSAGE_URI = "Main?action=project_add_message";
	
	public static final String PAGE_PERMISSION_DENIED_URI = "Main?action=permission_denied";
	
	public static final String PAGE_ORDER_LIST_URI = "Main?action=order_list";
	public static final String PAGE_ORDER_NEW_LIST_URI = "Main?action=order_new_list";
	public static final String PAGE_ORDER_SHOW_ADD_FORM_URI = "Main?action=order_show_add_form";
	public static final String PAGE_ORDER_ADD_MESSAGE_URI = "Main?action=order_add_message";
	
	public static final String PAGE_USER_VIEW_URI = "Main?action=user_view";
	public static final String PAGE_USER_LIST_URI = "Main?action=user_list";
	public static final String PAGE_LOGOUT = "Main?action=logout";
	
	public static final String PAGE_SHOW_AUTHORIZATION_FORM_URI = "Main?action=login_show_form";
	public static final String PAGE_EMPTY_URI = "Main?action=empty";
	
	public static final String PAGE_DEFAULT_MANAGER = PAGE_ORDER_NEW_LIST_URI;
	public static final String PAGE_DEFAULT_DEVELOPER = PAGE_PROJECT_LIST_BY_EMPLOYEE_URI;
	public static final String PAGE_DEFAULT_CUSTOMER = PAGE_ORDER_LIST_URI;
	public static final String PAGE_DEFAULT_ADMIN = PAGE_USER_LIST_URI;
	
	public static final String REQUEST_PARAM_ACTION = "action";
	
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
	public static final String REQUEST_PARAM_PROJECT_LIST_VO = "project_list_vo";
	
	public static final String REQUEST_PARAM_PROJECT_TITLE = "title";
	public static final String REQUEST_PARAM_PROJECT_DESCRIPTION = "description";
	public static final String REQUEST_PARAM_PROJECT_EMPLOYEE = "employee";
	public static final String REQUEST_PARAM_PROJECT_HOURS = "hours";
	
	public static final String REQUEST_PARAM_EMPLOYEE_LIST = "employee_list";
	
	public static final String REQUEST_PARAM_USER_VO = "user_vo";
	public static final String REQUEST_PARAM_USER_LIST_VO = "user_list_vo";
	
	public static final String JSPF_FOLDER = "fragment/";
	public static final String REQUEST_PARAM_XHR = "xhr";
	public static final String REQUEST_PARAM_JSPF = "jspf";
	
	public static final String PAGE_ORDER_VIEW_FRAGMENT = JSP_FOLDER_PATH + JSPF_FOLDER + "orderView.jsp";
	
	public static final String REQUEST_PARAM_URI = "uri";
	
	public static final String SESSION_PARAM_USER = "user";
}
