package by.htp.devteam.service.util;

/**
 * Constants for service layer: messages error for logger and for exception
 * @author julia
 *
 */
public final class ConstantValue {

	private ConstantValue() {
		super();
	}
	
	public static final String MSG_ERROR_FILL_LOGIN_OR_PASSWORD = "fill login or password";
	public static final String MSG_ERROR_UPLOAD_FILE = "Cannot upload file";
	public static final String MSG_ERROR_DELETE_FILE = "Cannot delete file";
	
	public static final String MSG_LOGGER_FILL_LOGIN_OR_PASSWORD = "User cannot fill login or (and) password";
	public static final String MSG_LOGGER_USER_NOT_FOUND = "User {} not found";
	public static final String MSG_LOGGER_USER_INCORRECT_PASSWORD = "User {}. Incorrect password";
	
	public static final String MSG_LOGGER_PAGE_NUMBER_NOT_FOUND = "Cannot page number found";
	public static final String MSG_LOGGER_ORDER_ADD_INCORRECT_FIELD = "Order add. Incorrect fields values";

	public static final String MSG_LOGGER_ORDER_VIEW_INCORRECT_ID = "Order view. Incorrect id {} value";
	public static final String MSG_LOGGER_ORDER_VIEW_NOT_EXIST_ID = "Order view. Not exist id {} ";
	public static final String MSG_LOGGER_PROJECT_ADD_INCORRECT_FIELD = "Project add. Incorrect fields values";
	
	public static final String MSG_LOGGER_PROJECT_ADD_INCORRECT_FIELD_EMPLOYEE = "Project add. "
			+ "Incorrect fields values for employee field";
	
	public static final String MSG_LOGGER_PROJECT_ADD_NO_ISSET_FREE_EMPLOYEE = "Project add. "
			+ "No iset free employee";
	
	public static final String MSG_LOGGER_PROJECT_VIEW_INCORRECT_ID = "Project view. Incorrect id {} value";
	public static final String MSG_LOGGER_PROJECT_ADD_INCORRECT_ORDER_ID = "Project add. Incorrect id {} value for order";
	public static final String MSG_LOGGER_PROJECT_UPDATE_HOURS_INCORRECT_PROJECT_ID = "Project update hours. Incorrect id {} value for project";
	public static final String MSG_LOGGER_PROJECT_UPDATE_HOURS_INCORRECT_FIELD = "Project update hours. Incorrect field value for project {}";
	public static final String MSG_LOGGER_PROJECT_VIEW_NOT_EXIST_ID = "Project view. Not exist id {} ";
	
	public static final String MSG_LOGGER_PROJECT_FIND_TITLE_TOO_SHORT = "Project's title {} too short";
	
	
	
	
	
}
