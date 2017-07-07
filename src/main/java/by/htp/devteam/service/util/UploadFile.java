package by.htp.devteam.service.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.Part;

import static by.htp.devteam.util.SettingConstantValue.*;

final public class UploadFile {
	
	private String applicationPath;
	public final static String uploadPath = UPLOAD_DIR + File.separator;
	private String fullUploadPath;

	public UploadFile(String applicationPath) {
		super();
		this.applicationPath = applicationPath;
		this.fullUploadPath = this.applicationPath + File.separator + uploadPath ;
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

	/*public String getFileName1(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }*/
	
	public String upload(Part part, String fileName) throws FileUploadException {

		System.out.println("fileName="+getFileName(part));
        // constructs path of the directory to save uploaded file
     // creates the save directory if it does not exists
        File fileSaveDir = new File(fullUploadPath);
        if ( !fileSaveDir.exists() ) {
            fileSaveDir.mkdirs();
        }
        
        System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());
 
        try {
        //Get all the parts from request and write it to the file on server
       // for (Part part : request.getParts()) {
            System.out.println(fileName);
            part.write(fullUploadPath + fileName);
       // }
        } catch (IOException e) {
        	throw new FileUploadException("can't upload file");
        }
        
        return fileName;
	}
	
	public void delete(String fileName) throws FileUploadException {		
		File file = new File(fullUploadPath + fileName);

		if ( !file.delete()) {
			throw new FileUploadException("Delete operation is failed.");
		}
	}
	
}
