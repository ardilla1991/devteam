package by.htp.devteam.util.jsp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Display error massage by code
 * @author julia
 *
 */
public class MessageTag extends TagSupport{
	
	private static final long serialVersionUID = 6316769356559277444L;
	
	/**
	 * Error code
	 * @see by.htp.devteam.service.util.ErrorCode
	 */
	private int errorCode;
	
	/**
	 * List of incorrect fields
	 * @see by.htp.devteam.service.ServiceException
	 */
	private List<String> msgList;
	
	/** Bean name for bundles text */
	private String bean;
	
	/** Current language */
	private String language;
	
	/** Current country */
	private String country;
	
	public MessageTag() {
		super();
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setMsgList(List<String> msgList) {
		this.msgList = msgList;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setBean(String bean) {
		this.bean = bean;
	}
	
	/**
	 * Display message
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			Locale locale = new Locale(language, country);
			ResourceBundle rb = ResourceBundle.getBundle("text", locale);
			
			pageContext.getOut().write("<div>");
			pageContext.getOut().write(rb.getString("error.code." + errorCode) + " ");
			printMsgList(rb);
			pageContext.getOut().write("</div>");
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
	
	private void printMsgList(ResourceBundle rb) throws IOException {
		if ( msgList == null )
			return;
		
		int msgListSize = msgList.size();
		if ( msgListSize == 0 ) {
			return;
		}
	
		for ( String msg : msgList) {
			pageContext.getOut().write("<span>" + rb.getString(bean + "." + msg) + "</span>, ");
		}
	}

}
