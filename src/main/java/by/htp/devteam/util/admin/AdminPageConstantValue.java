package by.htp.devteam.util.admin;

public final class AdminPageConstantValue {
	
	private AdminPageConstantValue() {
		
	}
	
	public static final String JSP_FOLDER_PATH = "/WEB-INF/jsp/Admin/";
	
	public static final String PAGE_DEFAULT = "index.html";
	
	public static final String PAGE_ERROR = JSP_FOLDER_PATH + "error.jsp";
	
	public static final String PAGE_LOGIN = JSP_FOLDER_PATH + "index.jsp";
	public static final String PAGE_PERMISSION_DENIED = JSP_FOLDER_PATH + "permissionDenied.jsp";
	
	public static final String ORDER_NEW_LIST = JSP_FOLDER_PATH + "ordersNewList.jsp";
}
