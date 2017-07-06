package by.htp.devteam.service.util;

public class FileUploadException extends Exception{

	private static final long serialVersionUID = 4975340870978126108L;
	private Exception _hidden;
	
	public FileUploadException(String s) {
		super(s);
	}
	
	public FileUploadException(String s, Exception e) {
		super(s);
		_hidden = e;
	}
	
	public Exception getCommandExeption() {
		return _hidden;
	}
}
