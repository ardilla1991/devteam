package by.htp.devteam.service.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Encrypting for users password. Use BCrypt encryption
 * @author julia
 *
 */
public final class Encrypting {
	
	private Encrypting() {
		super();
	}
	
	/**
	 * Password encrypting
	 * @param string String for coding
	 * @return passwords hash
	 */
	public static String getCode(String string) {
		String pw_hash = BCrypt.hashpw(string, BCrypt.gensalt());
		return pw_hash;
	}
	
	/**
	 * Check if passwords from DB and from form are the same
	 * @param password Password from form
	 * @param storedPassword Password from DB
	 * @return if correct password or not
	 */
	public static boolean isCorrectPassword(String password, String storedPassword) {
		return BCrypt.checkpw(password, storedPassword);
	}
}
