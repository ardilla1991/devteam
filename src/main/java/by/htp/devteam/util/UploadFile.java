package by.htp.devteam.util;

import java.io.File;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import static by.htp.devteam.util.SettingConstantValue.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

final public class UploadFile {
	
	private final static UploadFile instance = new UploadFile();
	
	private UploadFile() {
		super();
	}
	
	public static UploadFile getInstance() {
		return instance;
	}
	
	public FileItem getFile(HttpServletRequest request, String fieldName) {
		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		factory.setSizeThreshold(FILE_UPLOAD_MAX_MEMORY_SIZE);
		// Configure a repository (to ensure a secure temp location is used)
		
		ServletContext servletContext = request.getSession(false).getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		
		System.out.println("repository=" + repository);
		
		factory.setRepository(FILE_UPLOAD_TMP_DIR);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(FILE_UPLOAD_MAX_REQUEST_SIZE);

		// Parse the request
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		System.out.println("Files");
		System.out.println(items.get(0));
		
		return items.get(0);
	}
	
	public String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
	
	
}
