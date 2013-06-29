package com.gy.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *  Get properties value that under classPath's *.properties
 *  Note:	encoding=utf-8
 * @author liqiang 2013-6-29
 *
 */
public class PropertiesUtil {
	private static String propertiesFileAddress;
	private InputStream inStream;
	
	public static PropertiesUtil getInstance(String propName) {
		PropertiesUtil.propertiesFileAddress = propName;
		return new PropertiesUtil();
	}
	
	private PropertiesUtil(){}
	
	public String getValue(String key) {
		Properties props = new Properties();
		try {
			inStream = Class.class.getResourceAsStream("/"+propertiesFileAddress);
			props.load(inStream);
			return props.getProperty(key);
		} catch (IOException e) {
			throw new RuntimeException("您传入的propertiesFileAddress="+propertiesFileAddress+"路径不对!", e);
		} finally {
			if(inStream!=null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
