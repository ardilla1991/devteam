package by.htp.devteam.bean;

/**
 * Enum of user's roles in system
 * @author julia
 *
 */
public enum UserRole {
	MANAGER, DEVELOPER, CUSTOMER, GUEST, ADMIN;
	
	/**
	 * Find role in enam
	 * @param role
	 * @return boolean
	 */
	public static boolean lookup(String role) {
		String enumRole = role.toUpperCase();
		boolean found = false;
        for ( UserRole enumElement: values() ){
           if ( enumElement.toString().equals(enumRole) ) 
        	   found = true;
        }  
		return found;
	}
}
