package com.test.mybatis.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class XmlParser {
	
	
	
	public static Map<String,xmlEntity> parseSqlXml(String path){
	Map<String,xmlEntity> map = new HashMap();
	ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	try {
		Resource[] resources = resolver.getResources(path);
		List<xmlEntity> list = new ArrayList();
		for(Resource resource:resources) {
			File file = resource.getFile();
			if(file.getName().endsWith(".xml")) {
				SAXReader reader = new SAXReader();
				Document document = reader.read(file);
				Element root = document.getRootElement();
				//获取namespace
				String nameSpace = root.attribute("namespace").getValue();
				//获取所有的sql块
				List<Element> elements = root.elements();
				for(Element element:elements) {
					String sqlId = element.attribute("id").getValue();
					String resultType = element.attribute("resultType").getValue();
					String parameterType = element.attribute("parameterType").getValue();
					String sql = element.getStringValue();
					
					xmlEntity entity = new xmlEntity();
					entity.setNameSpace(nameSpace);
					entity.setParameterType(parameterType);
					entity.setResultType(resultType);
					entity.setSql(sql);
					entity.setSqlId(sqlId);
					list.add(entity);
				}
				buildResultMap(map, list);
			}
		}
	    } catch (Exception e) {
		  e.printStackTrace();
	  }
	return map;	
	}
	
	private static Map<String,xmlEntity> buildResultMap(Map<String,xmlEntity> map,List<xmlEntity> list){
		  if(list.size()>0) {
			  for(xmlEntity entity:list) {
				  map.put(entity.getNameSpace()+"."+entity.getSqlId(), entity);
			  }
		  }
		  return map;
	}
	
	
     
	
}
