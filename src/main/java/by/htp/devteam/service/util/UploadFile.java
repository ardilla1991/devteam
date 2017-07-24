package by.htp.devteam.service.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.Part;

import by.htp.devteam.util.ConfigProperty;

import static by.htp.devteam.service.util.ConstantValue.*;

/**
 * Upload file. Save file in projects directory
 * @author julia
 * @todo Must by a static! If we don't pass the applicationPath each time
 *
 */
public final class UploadFile {
	
	private final static UploadFile instance = new UploadFile();
	
	/** Path to save file */
	public final static String uploadPath = ConfigProperty.INSTANCE.getStringValue(CONFIG_FILE_UPLOAD_DIR) + File.separator;
	
	/** Applications path */
	private final static String applicationPath = ConfigProperty.INSTANCE.getStringValue(CONFIG_APPLICATION_FULL_PATH);
	
	/** Full path to save file */
	private final static String fullUploadPath = applicationPath + File.separator + uploadPath;

	private UploadFile() {
		super();
	}
	
	public static UploadFile getInstance() {
		return instance;
	}
	
    /**
     * Utility method to get file name from HTTP header content-disposition
     */
	public String getFileName(Part part) {
		for ( String cd : part . getHeader("content-disposition") . split(";") ) {
			if ( cd.trim().startsWith("filename") ) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		
		return "";
	}
	
	/**
	 * Upload file
	 * @param part Part() field value
	 * @param fileName File name
	 * @return File name
	 * @throws FileUploadException
	 */
	public String upload(Part part, String fileName) throws FileUploadException {
		//System.out.println("fileName="+getFileName(part));
        // constructs path of the directory to save uploaded file
		// creates the save directory if it does not exists
        File fileSaveDir = new File(fullUploadPath);
        if ( !fileSaveDir.exists() ) {
            fileSaveDir.mkdirs();
        }
        
        //System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());
 
        try {
            part.write(fullUploadPath + fileName);
        } catch (IOException e) {
        	throw new FileUploadException(MSG_ERROR_UPLOAD_FILE);
        }
        
        return fileName;
	}
	
	/**
	 * Delete file from folder
	 * @param fileName file name
	 * @throws FileUploadException
	 */
	public void delete(String fileName) throws FileUploadException {		
		File file = new File(fullUploadPath + fileName);

		if ( !file.delete()) {
			throw new FileUploadException(MSG_ERROR_DELETE_FILE);
		}
	}
	
}
