package by.htp.devteam.service.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	private static final Pattern DECIMAL_PATTERN = Pattern.compile("^[0-9]{1,8}(\\.[0-9]{1,2})?$");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile("([^\\s]+(\\.(?i)(rar|zip|doc|docx|odt|txt))$)");
	
	private Validator() {
		super();
	}
	
	public static boolean isEmpty(String string) {
		return string.isEmpty();
	}
	
	public static boolean isDate(String string) {
        try {
            return DATE_FORMAT.format(DATE_FORMAT.parse(string)).equals(string);
        }catch (ParseException ex){
            return false;
        }
	}
	
	public static boolean checkBigDecimal(String string) {
		Matcher matcher = DECIMAL_PATTERN .matcher(string);
		
		return matcher.find();
	}
	
	public static boolean isLong(String string) {
		try {
			return Long.parseUnsignedLong(string) > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isInt(String string) {
		try {
			return Integer.parseUnsignedInt(string) > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isCorrectFileExtension(String string) {
		Matcher matcher = FILE_EXTENSION_PATTERN .matcher(string);
		
		return matcher.find();
	}
	
	
}
