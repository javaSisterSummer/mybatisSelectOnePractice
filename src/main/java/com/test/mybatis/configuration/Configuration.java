package com.test.mybatis.configuration;

import java.lang.reflect.Proxy;
import java.util.Map;

import com.test.mybatis.properties.PropertiesParser;
import com.test.mybatis.proxy.MapperProxy;
import com.test.mybatis.sqlSession.SqlSession;
import com.test.mybatis.xml.XmlParser;
import com.test.mybatis.xml.xmlEntity;

public class Configuration {
	
	public static Map<String,String> propertiesParseMap;
	
	public static Map<String,xmlEntity> sqlParseMap;
	
	static {
		init();
	}
	
	private static void init() {
		propertiesParseMap = PropertiesParser.parseProperties("application");
		sqlParseMap = XmlParser.parseSqlXml(propertiesParseMap.get("mybatis.mapper-locations"));
	}

	public <T> T getMapper(Class clazz,SqlSession sqlSession) {
		
		return (T) Proxy.newProxyInstance(
				  this.getClass().getClassLoader(), 
	              new Class[] {clazz}, 
	              new MapperProxy(sqlSession));
				
	}

}
