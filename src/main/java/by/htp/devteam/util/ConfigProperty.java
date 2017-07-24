package by.htp.devteam.util;

import java.util.ResourceBundle;

import static by.htp.devteam.service.util.ConstantValue.*;

/**
 * Singleton to get config properties from bundle
 * @author julia
 *
 */
public enum ConfigProperty {
	
	INSTANCE;
	
	private ResourceBundle resourceBundle;
	private final String resourceName = RESOURCE_CONFIG_BUNDLE;
	
	private ConfigProperty() {
		resourceBundle = ResourceBundle.getBundle(resourceName);
	}
	
	public String getStringValue(String key) {
		return resourceBundle.getString(key);
	}
	
	public int getIntValue(String key) {
		return Integer.valueOf(resourceBundle.getString(key));
	}
	
}
