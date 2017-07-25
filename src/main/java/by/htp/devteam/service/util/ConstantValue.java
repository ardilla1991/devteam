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
	public static final String MSG_LOGGER_USER_ADD_INCORRECT_FIELD = "User add. Incorrect fields values";
	public static final String MSG_LOGGER_USER_ISSET = "User add. User already exist";
	
	public static final String MSG_LOGGER_PAGE_NUMBER_NOT_FOUND = "Cannot page number found";
	public static final String MSG_LOGGER_ORDER_ADD_INCORRECT_FIELD = "Order add. Incorrect fields values";

	public static final String MSG_LOGGER_ORDER_VIEW_INCORRECT_ID = "Order view. Incorrect id {} value";
	public static final String MSG_LOGGER_ORDER_VIEW_NOT_EXIST_ID = "Order view. Not exist id {} ";
	public static final String MSG_LOGGER_PROJECT_ADD_INCORRECT_FIELD = "Project add. Incorrect fields values";
	
	public static final String MSG_LOGGER_PROJECT_ADD_INCORRECT_FIELD_EMPLOYEE = "Project add. "
			+ "Incorrect fields values for employee field";
	
	public static final String MSG_LOGGER_PROJECT_ADD_NO_ISSET_FREE_EMPLOYEE = "Project add. "
			+ "No isset free employee";
	
	public static final String MSG_LOGGER_PROJECT_VIEW_INCORRECT_ID = "Project view. Incorrect id {} value";
	public static final String MSG_LOGGER_PROJECT_ADD_INCORRECT_ORDER_ID = "Project add. Incorrect id {} value for order";
	public static final String MSG_LOGGER_PROJECT_UPDATE_HOURS_INCORRECT_PROJECT_ID = "Project update hours. Incorrect id {} value for project";
	public static final String MSG_LOGGER_PROJECT_UPDATE_HOURS_INCORRECT_FIELD = "Project update hours. Incorrect field value for project {}";
	public static final String MSG_LOGGER_PROJECT_VIEW_NOT_EXIST_ID = "Project view. Not exist id {} ";
	public static final String MSG_LOGGER_PROJECT_SEND_MAIL = "Send bill to customer ";
	
	public static final String MSG_LOGGER_PROJECT_FIND_TITLE_NOT_CORRECT_LENGTH = "Project's title {} has not correct length";
	

	public static final String MSG_LOGGER_EMPLOYEE_ADD_INCORRECT_FIELD = "Employee add. Incorrect fields values";
	public static final String MSG_LOGGER_EMPLOYEE_VIEW_INCORRECT_ID = "Employee view. Incorrect id {} value";
	public static final String MSG_LOGGER_EMPLOYEE_VIEW_NOT_EXIST_ID = "Employee view. Not exist id {} ";
	public static final String MSG_LOGGER_EMPLOYEE_USER_ALREADY_EXIST = "Set user for employee. "
			+ "User already exist";

	public static final String RESOURCE_CONFIG_BUNDLE = "config";
	public static final String CONFIG_PAGE_START_PAGE = "page.start_page";
	public static final String CONFIG_PAGE_COUNT_PER_PAGE = "page.count_per_page";
	public static final String CONFIG_FILE_UPLOAD_DIR = "file.upload.dir";
	public static final String CONFIG_APPLICATION_FULL_PATH = "application.full_path";
	public static final String CONFIG_EMAIL_SMTP_EMAIL = "email.smtp.email";
	public static final String CONFIG_EMAIL_SMTP_PASSWORD = "email.smtp.password";
	public static final String CONFIG_EMAIL_SMTP_HOST = "email.smtp.host";
	public static final String CONFIG_EMAIL_SMTP_PORT = "email.smtp.port";
	public static final String CONFIG_EMAIL_SMTP_AUTH = "email.smtp.auth";
	public static final String CONFIG_EMAIL_SMTP_SMARTTLS_ENABLE = "email.smtp.starttls.enable";
	public static final String CONFIG_EMAIL_FROM = "email.from";
	public static final String CONFIG_EMAIL_CHARSET = "email.charset";
	public static final String CONFIG_EMAIL_SITENAME = "email.sitename";
	public static final String MAIL_BODY_NEWLINE = "\n";
	public static final String RESOURCE_TEXT_BUNDLE = "text";
	public static final String RESOURCE_MAIL_BODY_FIELD_ORDER_TITLE = "order.bill.title";
	public static final String RESOURCE_MAIL_BODY_FIELD_ORDER_NAME = "order.bill.orderName";
	public static final String RESOURCE_MAIL_BODY_FIELD_ORDER_DATESTART = "order.bill.orderDateStart";
	public static final String RESOURCE_MAIL_BODY_FIELD_ORDER_DATEFINISH = "order.bill.orderDateFinish";
	public static final String RESOURCE_MAIL_BODY_FIELD_ORDER_PRICE = "order.bill.orderPrice";
	public static final String RESOURCE_EMAIL_SUBJECT = "email.subject";

	
	
}
