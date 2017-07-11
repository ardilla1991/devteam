package by.htp.devteam.util.jsp;

/**
 * Methods for jsp
 * @author julia
 *
 */
public class JspPlugin {
	
	/**
	 * Check if isset needle string in array of strings values
	 * @param needle needle value
	 * @param haystack Array of strings values
	 * @return boolean
	 */
	public static boolean inArray(String needle, Object haystack) {
		if ( haystack == null || needle == null )
			return false;
		
		String[] haystackArray = (String[]) haystack;
		boolean inArray = false;
		int haystackLength = haystackArray.length;
		for ( int i = 0; i < haystackLength; i++) {
			if ( needle.equals(haystackArray[i]) ) {
				inArray = true;
				break;
			}
		}

	    return inArray;
	}
}
