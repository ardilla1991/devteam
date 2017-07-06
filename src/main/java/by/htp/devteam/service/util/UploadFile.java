package by.htp.devteam.service.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.Part;

import static by.htp.devteam.util.SettingConstantValue.*;

final public class UploadFile {
	
	private String applicationPath;
	
	public UploadFile(String applicationPath) {
		super();
		this.applicationPath = applicationPath;
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
	
	public void upload(Part part) throws FileUploadException {

		System.out.println("fileName="+getFileName(part));
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        
     // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if ( !fileSaveDir.exists() ) {
            fileSaveDir.mkdirs();
        }
        
        System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());
 
        String fileName = null;
        try {
        //Get all the parts from request and write it to the file on server
       // for (Part part : request.getParts()) {
            fileName = getFileName(part);
            System.out.println(fileName);
            part.write(uploadFilePath + File.separator + fileName);
       // }
        } catch (IOException e) {
        	throw new FileUploadException("can't upload file");
        }
	}
	
	public void delete(String fileName) throws FileUploadException {
		
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
		
		File file = new File(uploadFilePath + File.separator + fileName);

		if ( !file.delete()) {
			throw new FileUploadException("Delete operation is failed.");
		}
	}
	
}
