package com.test.mybatis.properties;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PropertiesParser {
	
	public static Map<String,String> parseProperties(String propertiesName){
		Map<String,String> map = new HashMap();
		ResourceBundle  bundle = ResourceBundle.getBundle(propertiesName);
		Enumeration<String> keys = bundle.getKeys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put(key, bundle.getString(key));
		}
		return map;		
	}

}
