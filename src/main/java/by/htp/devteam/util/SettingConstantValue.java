package by.htp.devteam.util;

import java.io.File;

/**
 * Settings for project
 * @author julia
 *
 */
public final class SettingConstantValue {
	
	private SettingConstantValue() {
		super();
	}
	
	public static final int START_PAGE = 1;
	public static final int COUNT_PER_PAGE = 10;
	public static final int FILE_UPLOAD_MAX_MEMORY_SIZE = 2000;
	public static final File FILE_UPLOAD_TMP_DIR = new File("/home/yuliana/tmpfiles");
	public static final int FILE_UPLOAD_MAX_REQUEST_SIZE = 15000;
	public static final String UPLOAD_DIR = "uploads";
	
}
