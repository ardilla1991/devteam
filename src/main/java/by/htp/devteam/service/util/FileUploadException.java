package by.htp.devteam.service.util;

/**
 * Exception type for uploading file
 * @author julia
 *
 */
public class FileUploadException extends Exception{

	private static final long serialVersionUID = 4353293505990550455L;

	public FileUploadException(String message) {
		super(message);
	}
	
	public FileUploadException(String message, Exception e) {
		super(message, e);
	}
	
	public FileUploadException(Exception e) {
		super(e);
	}
}
