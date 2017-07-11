package by.htp.devteam.service.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator of common types.
 * @author julia
 *
 */
public final class Validator {

	/** Regular expression for decimal numbers */
	private static final Pattern DECIMAL_PATTERN = Pattern.compile("^[0-9]{1,8}(\\.[0-9]{1,2})?$");
	
	/** Date format */
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	/** Regular expression for file extension */
	private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile("([^\\s]+(\\.(?i)(rar|zip|doc|docx|odt|txt))$)");
	
	private Validator() {
		super();
	}
	
	/**
	 * Check if string is empty
	 * @param string
	 * @return boolean
	 */
	public static boolean isEmpty(String string) {
		return string.isEmpty();
	}
	
	/**
	 * Check if string is has correct date format
	 * @param string
	 * @return boolean
	 */
	public static boolean isDate(String string) {
        try {
            return DATE_FORMAT.format(DATE_FORMAT.parse(string)).equals(string);
        }catch (ParseException ex){
            return false;
        }
	}
	
	/**
	 * Check if a string has a correct decimal format
	 * @param string
	 * @return boolean
	 */
	public static boolean checkBigDecimal(String string) {
		Matcher matcher = DECIMAL_PATTERN .matcher(string);
		
		return matcher.find();
	}
	
	/**
	 * Check if a string has a correct Long format
	 * @param string
	 * @return boolean
	 */
	public static boolean isLong(String string) {
		try {
			return Long.parseUnsignedLong(string) > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Check if a string has a correct int format
	 * @param string
	 * @return boolean
	 */
	public static boolean isInt(String string) {
		try {
			return Integer.parseUnsignedInt(string) > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Check if file has a correct extension
	 * @param string
	 * @return boolean
	 */
	public static boolean isCorrectFileExtension(String string) {
		Matcher matcher = FILE_EXTENSION_PATTERN .matcher(string);
		
		return matcher.find();
	}
	
	
}
