package by.htp.devteam.util.jsp;

public class JspPlugin {
	
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
