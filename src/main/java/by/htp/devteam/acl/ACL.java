package by.htp.devteam.acl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.UserRole;
import by.htp.devteam.controller.ControllerExeption;
import by.htp.devteam.controller.ControllerFactory;

/**
 * Class for create acl for users and check if user has permission for action
 * Singleton class.
 * @author julia
 *
 */
public final class ACL {

	/** ACL for users */
	private final static Map<UserRole, Map<ControllerFactory, List<String>>> acl = new HashMap<UserRole, Map<ControllerFactory, List<String>>>();
	
	/** 
	 * ACL for guest. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.controller.ControllerFactory
	 */
	private final static Map<ControllerFactory, List<String>> guestACL = new HashMap<ControllerFactory, List<String>>();
	
	/** 
	 * ACL for manager. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.controller.ControllerFactory
	 */
	private final static Map<ControllerFactory, List<String>> managerACL = new HashMap<ControllerFactory, List<String>>();
	
	/** 
	 * ACL for developer. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.controller.ControllerFactory
	 */
	private final static Map<ControllerFactory, List<String>> developerACL = new HashMap<ControllerFactory, List<String>>();
	
	/** 
	 * ACL for customer. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.controller.ControllerFactory
	 */
	private final static Map<ControllerFactory, List<String>> customerACL = new HashMap<ControllerFactory, List<String>>();
	
	/** 
	 * ACL for admin. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.controller.ControllerFactory
	 */
	private final static Map<ControllerFactory, List<String>> adminACL = new HashMap<ControllerFactory, List<String>>();
	
	private final static ACL instance = new ACL();
	
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
		List<String> userActions = new ArrayList<String>(5);
		userActions.add("login");
		userActions.add("message");
		guestACL.put(ControllerFactory.USER, userActions);

		acl.put(UserRole.GUEST, guestACL);
	}
	
	private void setManagersACL() {
		List<String> userActions = new ArrayList<String>(5);
		userActions.add("login");
		userActions.add("logout");
		userActions.add("view");
		managerACL.put(ControllerFactory.USER, userActions);
		
		List<String> employeeActions = new ArrayList<String>(5);
		employeeActions.add("list");
		employeeActions.add("add");
		employeeActions.add("message");
		managerACL.put(ControllerFactory.EMPLOYEE, employeeActions);
		
		List<String> orderActions = new ArrayList<String>(5);
		orderActions.add("new_list");
		orderActions.add("view");
		managerACL.put(ControllerFactory.ORDER, orderActions);
		
		List<String> projectActions = new ArrayList<String>(5);
		projectActions.add("list");
		projectActions.add("add");
		projectActions.add("message");
		projectActions.add("find");
		projectActions.add("view");
		managerACL.put(ControllerFactory.PROJECT, projectActions);
		
		acl.put(UserRole.MANAGER, managerACL);
	}
	
	private void setDevelopersACL() {
		List<String> userActions = new ArrayList<String>(5);
		userActions.add("login");
		userActions.add("logout");
		userActions.add("view");
		developerACL.put(ControllerFactory.USER, userActions);

		List<String> projectActions = new ArrayList<String>(5);
		projectActions.add("list_by_employee");
		projectActions.add("update_hours");
		projectActions.add("message");
		projectActions.add("find");
		projectActions.add("view");
		developerACL.put(ControllerFactory.PROJECT, projectActions);
		
		acl.put(UserRole.DEVELOPER, developerACL);
	}
	
	private void setCustomersACL() {
		List<String> userActions = new ArrayList<String>(5);
		userActions.add("login");
		userActions.add("logout");
		userActions.add("view");
		customerACL.put(ControllerFactory.USER, userActions);
		
		List<String> orderActions = new ArrayList<String>(5);
		orderActions.add("list");
		orderActions.add("add");
		orderActions.add("message");
		orderActions.add("view");
		customerACL.put(ControllerFactory.ORDER, orderActions);
		
		acl.put(UserRole.CUSTOMER, customerACL);
	}
	
	private void setAdminACL() {
		List<String> userActions = new ArrayList<String>(5);
		userActions.add("login");
		userActions.add("logout");
		userActions.add("view");
		userActions.add("list");
		userActions.add("add");
		userActions.add("message");
		adminACL.put(ControllerFactory.USER, userActions);
		
		acl.put(UserRole.ADMIN, adminACL);
	}

	/**
	 * Check if user has permissions for module and action.
	 * @param role
	 * @param module
	 * @param action
	 * @return boolean
	 */
	public boolean issetInACL(UserRole role, String module, String action) {
		if ( module == null || action == null )
			return false;
		
		boolean issetInACL = false;
		try {
			issetInACL = ControllerFactory.lookup(module) 
					&& acl.get(role).containsKey(ControllerFactory.getController(module)) 
					&& acl.get(role).get(ControllerFactory.getController(module)).contains(action);
		} catch (ControllerExeption e) {
			issetInACL = false;
		}

		return issetInACL;
	}
	
}
