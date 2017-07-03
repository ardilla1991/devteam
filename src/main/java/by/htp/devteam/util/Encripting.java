package by.htp.devteam.util;

import org.mindrot.jbcrypt.BCrypt;

public class Encripting {
	
	public static String getCode(String string) {
		String pw_hash = BCrypt.hashpw(string, BCrypt.gensalt());
		return pw_hash;
	}
	
	public static boolean isCorrectPassword(String password, String storedPassword) {
		if ( BCrypt.checkpw(password, storedPassword) )
		    return true;
		else
		    return false;
	}
}
