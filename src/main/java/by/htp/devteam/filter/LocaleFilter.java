package by.htp.devteam.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LocaleFilter implements Filter{

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Locale current = response.getLocale();
		String countryLanguage = current.getLanguage();
		//System.out.println("countryL="+countryLanguage);
		if ( !"ru".equals(countryLanguage) && !"en".equals(countryLanguage)) {
			Locale.setDefault(Locale.US);
			response.setLocale(Locale.getDefault());
			countryLanguage = "en";
		}
		//System.out.println("curr="+response.getLocale());
		Locale.setDefault(Locale.US);
		request.setAttribute("localeLanguage", countryLanguage);
		
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
