package by.htp.devteam.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	private final static Pattern DECIMAL_PATTERN = Pattern.compile("^(\\d*\\.)?\\d+$");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
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
	
	
}
