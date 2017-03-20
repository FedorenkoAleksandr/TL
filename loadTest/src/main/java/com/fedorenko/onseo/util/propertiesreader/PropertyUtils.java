package com.fedorenko.onseo.util.propertiesreader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The {@code PropertyUtils} class represents a Map of configuration 
 * file. 
 * @author  Fedorenko Oleksandr
 */
public class PropertyUtils {
	
	private static final Log logger = LogFactory.getLog(PropertyUtils.class);
	
	private static final String URL_VALUE = "url";
	private static final String URL_DEFAULT_VALUE = "http://localhost:8080";
	private static final String REPORT_VALUE = "report";
	private static final String REPORT_DEFAULT_VALUE = "requestPerSecondReport";
	private static final String REQUEST_PER_SECOND_VALUE = "requestPerSeconds";
	private static final String REQUEST_PER_SECOND_DEFAULT_VALUE = "301";
	
	private Map<String, String> propertiesMap;
	private String pathToConfig;
	
	/**
	 * Return value of properties represented in config file.
	 * 
	 * @param    String key the property key represent in config file
	 * @return   String value represent in config file with the specified key value
	 */
	public String getProperty(String key) {
		String value = null;
		if (StringUtils.isNotEmpty(key)) {
			value = propertiesMap.get(key);
		}
		if (StringUtils.isNotEmpty(value)) {
			return value;
		} else {
			logger.error("method getProperty() return null because of Key " + key + "doesn't exists");
		}
		return null;
	}
	
	/**
     * Loads all of the properties represented by the configuration
     * properties file. 
     */
	private void getConfiguration() {
		FileInputStream fileInputStream;
        Properties properties = new Properties();
        logger.info("Start to read properties from: " + pathToConfig);
        try {
            fileInputStream = new FileInputStream(pathToConfig);
            properties.load(fileInputStream);
            propertiesMap = new HashMap<>();
            Enumeration<Object> keys = properties.keys();
            logger.info("Start to read main components:");
            while(keys.hasMoreElements()){
            	String key = (String) keys.nextElement();
            	String value = properties.getProperty(key);
            	propertiesMap.put(key, value);
            }  
        } catch (IOException e) {
        	logger.error("Invalid path to Config.properties file. "
        			    + "Your path is: " + pathToConfig);
            e.printStackTrace();
        }
        checkMainProperties();
        logger.info("Config file had been read successfully.");
	}
	
	/**
	 * Check if main properties represent by configuration properties
	 * file, if no set default value.
	 */
	private void checkMainProperties() {
		if(propertiesMap.get(REQUEST_PER_SECOND_VALUE) == null) {
			logger.error("Undefind quantity of request, default value: "
					+ REQUEST_PER_SECOND_DEFAULT_VALUE + "will be set");
			propertiesMap.put(REQUEST_PER_SECOND_VALUE, REQUEST_PER_SECOND_DEFAULT_VALUE); 
		}
		if(propertiesMap.get(URL_VALUE) == null) {
			logger.error("Undefind url, default value: "
					+ URL_DEFAULT_VALUE + "will be set");
			propertiesMap.put(URL_VALUE, URL_DEFAULT_VALUE); 
		}
		if(propertiesMap.get(REPORT_VALUE) == null) {
			logger.error("Undefind report value, default value: "
					+ REPORT_DEFAULT_VALUE + "will be set");
			propertiesMap.put(REPORT_VALUE, REPORT_DEFAULT_VALUE); 
		}
	}
	
	public String getPathToConfig() {
		return pathToConfig;
	}
	
	public void setPathToConfig(String pathToConfig) {
		this.pathToConfig = pathToConfig;
	}
}