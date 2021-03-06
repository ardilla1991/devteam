package by.htp.devteam.acl;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.UserRole;
import by.htp.devteam.controller.ControllerException;
import by.htp.devteam.controller.ControllerFactory;

/**
 * Class for create acl for users and check if user has permission for action
 * Singleton class.
 * @author julia
 *
 */
public final class ACL {
	
	private static final String ACTION_LOGIN = "login";
	private static final String ACTION_LOGOUT = "logout";
	private static final String ACTION_VIEW = "view";
	private static final String ACTION_ADD = "add";
	private static final String ACTION_LIST = "list";
	private static final String ACTION_MESSAGE = "message";
	private static final String ACTION_FIND = "find";
	private static final String ACTION_NEW_LIST = "new_list";
	private static final String ACTION_LIST_BY_EMPLOYEE = "list_by_employee";
	private static final String ACTION_UPDATE_HOURS = "update_hours";

	/** ACL for users */
	private static final EnumMap<UserRole, Map<ControllerFactory, List<String>>> aclList = new EnumMap<>(UserRole.class);
	
	/** 
	 * ACL for guest. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.controller.ControllerFactory
	 */
	private static final EnumMap<ControllerFactory, List<String>> guestACL = new EnumMap<>(ControllerFactory.class);
	
	/** 
	 * ACL for manager. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.controller.ControllerFactory
	 */
	private static final EnumMap<ControllerFactory, List<String>> managerACL = new EnumMap<>(ControllerFactory.class);
	
	/** 
	 * ACL for developer. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.controller.ControllerFactory
	 */
	private static final EnumMap<ControllerFactory, List<String>> developerACL = new EnumMap<>(ControllerFactory.class);
	
	/** 
	 * ACL for customer. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.controller.ControllerFactory
	 */
	private static final EnumMap<ControllerFactory, List<String>> customerACL = new EnumMap<>(ControllerFactory.class);
	
	/** 
	 * ACL for admin. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.controller.ControllerFactory
	 */
	private static final EnumMap<ControllerFactory, List<String>> adminACL = new EnumMap<>(ControllerFactory.class);
	
	private static final ACL instance = new ACL();
	
	private ACL() {
		super();
		init();
	}
	
	public static ACL getInstance() {
		return instance;
	}
	
	private void init() {
		setGuestsACL();
		setManagersACL();
		setDevelopersACL();
		setCustomersACL();
		setAdminACL();
	}
	
	private void setGuestsACL() {
		List<String> userActions = new ArrayList<>(5);
		userActions.add(ACTION_LOGIN);
		userActions.add(ACTION_MESSAGE);
		guestACL.put(ControllerFactory.USER, userActions);

		aclList.put(UserRole.GUEST, guestACL);
	}
	
	private void setManagersACL() {
		List<String> userActions = new ArrayList<>(5);
		userActions.add(ACTION_LOGIN);
		userActions.add(ACTION_LOGOUT);
		userActions.add(ACTION_VIEW);
		managerACL.put(ControllerFactory.USER, userActions);
		
		List<String> employeeActions = new ArrayList<>(5);
		employeeActions.add(ACTION_LIST);
		employeeActions.add(ACTION_ADD);
		employeeActions.add(ACTION_MESSAGE);
		managerACL.put(ControllerFactory.EMPLOYEE, employeeActions);
		
		List<String> orderActions = new ArrayList<>(5);
		orderActions.add(ACTION_NEW_LIST);
		orderActions.add(ACTION_VIEW);
		managerACL.put(ControllerFactory.ORDER, orderActions);
		
		List<String> projectActions = new ArrayList<>(5);
		projectActions.add(ACTION_LIST);
		projectActions.add(ACTION_ADD);
		projectActions.add(ACTION_MESSAGE);
		projectActions.add(ACTION_FIND);
		projectActions.add(ACTION_VIEW);
		managerACL.put(ControllerFactory.PROJECT, projectActions);
		
		aclList.put(UserRole.MANAGER, managerACL);
	}
	
	private void setDevelopersACL() {
		List<String> userActions = new ArrayList<>(5);
		userActions.add(ACTION_LOGIN);
		userActions.add(ACTION_LOGOUT);
		userActions.add(ACTION_VIEW);
		developerACL.put(ControllerFactory.USER, userActions);

		List<String> projectActions = new ArrayList<>(5);
		projectActions.add(ACTION_LIST_BY_EMPLOYEE);
		projectActions.add(ACTION_UPDATE_HOURS);
		projectActions.add(ACTION_MESSAGE);
		projectActions.add(ACTION_FIND);
		projectActions.add(ACTION_VIEW);
		developerACL.put(ControllerFactory.PROJECT, projectActions);
		
		aclList.put(UserRole.DEVELOPER, developerACL);
	}
	
	private void setCustomersACL() {
		List<String> userActions = new ArrayList<>(5);
		userActions.add(ACTION_LOGIN);
		userActions.add(ACTION_LOGOUT);
		userActions.add(ACTION_VIEW);
		customerACL.put(ControllerFactory.USER, userActions);
		
		List<String> orderActions = new ArrayList<>(5);
		orderActions.add(ACTION_LIST);
		orderActions.add(ACTION_ADD);
		orderActions.add(ACTION_MESSAGE);
		orderActions.add(ACTION_VIEW);
		customerACL.put(ControllerFactory.ORDER, orderActions);
		
		aclList.put(UserRole.CUSTOMER, customerACL);
	}
	
	private void setAdminACL() {
		List<String> userActions = new ArrayList<>(5);
		userActions.add(ACTION_LOGIN);
		userActions.add(ACTION_LOGOUT);
		userActions.add(ACTION_VIEW);
		userActions.add(ACTION_LIST);
		userActions.add(ACTION_ADD);
		userActions.add(ACTION_MESSAGE);
		adminACL.put(ControllerFactory.USER, userActions);
		
		aclList.put(UserRole.ADMIN, adminACL);
	}

	/**
	 * Check if user has permissions for module and action.
	 * @param role User's role
	 * @param module Module for checking permissions 
	 * @param action Action for checking permissions 
	 * @return boolean If isset permission
	 */
	public boolean issetInACL(UserRole role, String module, String action) {
		if ( module == null || action == null )
			return false;
		
		boolean issetInACL = false;
		try {
			issetInACL = ControllerFactory.lookup(module) 
					&& aclList.get(role).containsKey(ControllerFactory.getController(module)) 
					&& aclList.get(role).get(ControllerFactory.getController(module)).contains(action);
		} catch (ControllerException e) {
			issetInACL = false;
		}

		return issetInACL;
	}
	
}
