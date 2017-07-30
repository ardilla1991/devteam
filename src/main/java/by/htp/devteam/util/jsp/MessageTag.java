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
	
	/** Container tag for all points */
	private String containerTag;
	
	/** Item tag for point */
	private String itemTag;
	
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

	public void setContainerTag(String containerTag) {
		this.containerTag = containerTag;
	}
	
	public void setItemTag(String itemTag) {
		this.itemTag = itemTag;
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
			
			pageContext.getOut().write("<" + containerTag + ">");
			pageContext.getOut().write(getString(rb, "error.code." + errorCode) + " ");
			printMsgList(rb);
			pageContext.getOut().write("</" + containerTag + ">");
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
	
	/*
	 * Print fields with not correct values
	 */
	private void printMsgList(ResourceBundle rb) throws IOException {
		if ( msgList == null )
			return;
		
		int msgListSize = msgList.size();
		if ( msgListSize == 0 ) {
			return;
		}
	
		StringBuilder wr = new StringBuilder();
		String delimiter = "";
		for ( String msg : msgList) {
			wr.append(delimiter + "<" + itemTag + ">" + getString(rb, bean + "." + msg) + "</" + itemTag + ">");
			delimiter = ", ";
		}
		pageContext.getOut().write(wr.toString());
	}
	
	/*
	 * Check if exist key in bundle and return this value
	 */
	private String getString(ResourceBundle rb, String key) {
		if ( rb.containsKey(key) ) {
			return rb.getString(key);
		}
		
		return "";
	}

}
