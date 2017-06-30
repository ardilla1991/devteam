package by.htp.devteam.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	private final static Pattern DECIMAL_PATTERN = Pattern.compile("(\\d*\\.)?\\d+");
	
	private Validator() {
		super();
	}
	
	public static boolean isEmpty(String string) {
		return string.isEmpty();
	}
	
	public static boolean checkBigDecimal(String string) {
		Matcher matcher = DECIMAL_PATTERN .matcher(string);
		
		return matcher.find();
	}
	
	
}
