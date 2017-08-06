package by.htp.devteam.acl;

import static by.htp.devteam.controller.util.ConstantValue.*;

import java.util.Map;

import by.htp.devteam.bean.UserRole;
import by.htp.devteam.router.Router;

/**
 * Class to check if user has permission for uri
 * Singleton class.
 * @author julia
 *
 */
public final class PermissionUri {

	private final static PermissionUri instance = new PermissionUri();
	
	private PermissionUri() {
		super();
	}
	
	public static PermissionUri getInstance() {
		return instance;
	}
	

	/**
	 * Check if user has permission for url like module/action
	 * @param role
	 * @param uri
	 * @return boolean
	 */
	public boolean checkPermissionForUri(UserRole role, String uri) {
		//from module/action to module
		 Map<String, String> mainParams = Router.getInstance().getMainParametersFromUri(uri);
		 if ( mainParams.isEmpty() )
			 return false;
		 
		 return ACL.getInstance().issetInACL(role, mainParams.get(MODULE), mainParams.get(ACTION));
	}
}
