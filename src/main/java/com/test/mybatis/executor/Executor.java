package com.test.mybatis.executor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.test.mybatis.configuration.Configuration;
import com.test.mybatis.xml.xmlEntity;

public class Executor {
	
	
	public <T> T selectOne(xmlEntity entity, Object param) {
	    Connection conn = null;
		PreparedStatement statement = null;
		T newInstance = null;
		try {
			String sql = entity.getSql();
			List<String> paramList = new ArrayList();
			Pattern pattern = Pattern.compile("#\\{.+?}");
			Matcher matcher = pattern.matcher(sql);
			while(matcher.find()) {
				String group = matcher.group();
				paramList.add(group);
			}
		   if(paramList.size() == 1 && entity.getParameterType().equals("java.lang.String")) {
			   String replace = sql.replace(paramList.get(0),"?");
			   Class.forName("com.mysql.cj.jdbc.Driver");
			   conn = DriverManager.getConnection(
						Configuration.propertiesParseMap.get("spring.datasource.url"),
						Configuration.propertiesParseMap.get("spring.datasource.username"),
						Configuration.propertiesParseMap.get("spring.datasource.password"));
			   statement = conn.prepareStatement(replace);
			   statement.setString(1, param.toString());
			   
			   Map<String,Field> fieldMap = new HashMap();
			   String resultType = entity.getResultType();
			    Class<?> resultClass = Class.forName(resultType);
			    newInstance = (T) resultClass.newInstance();
			    Field[] fields = resultClass.getDeclaredFields();
			    for(Field field:fields) {
			    	fieldMap.put(field.getName(), field);			    	
			    }
			   ResultSet result = statement.executeQuery();
			   
			   Map<String,String> metaDataMap = new HashMap();
			   ResultSetMetaData metaData = result.getMetaData();
			   for(int i =1;i<=metaData.getColumnCount();i++) {
				   metaDataMap.put(metaData.getColumnLabel(i), metaData.getColumnTypeName(i));		  
			   }
			   
			   Set<Entry<String, String>> entrySet = metaDataMap.entrySet();
			   while(result.next()) {
				   for(Entry<String, String> entry:entrySet) {
					   String columnLabel = entry.getKey();
					   String columnTypeName = entry.getValue();
					   if("CHAR".equals(columnTypeName) || "VARCHAR".equals(columnTypeName)) {
						   String value = result.getString(columnLabel);
						   Field field = fieldMap.get(columnLabel);
						   field.setAccessible(true);
						   field.set(newInstance, value);
					   }	
				   }
			   }
		   }	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
					statement = null;
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					conn = null;
				}
			}
		}
		return newInstance;
	}
}
