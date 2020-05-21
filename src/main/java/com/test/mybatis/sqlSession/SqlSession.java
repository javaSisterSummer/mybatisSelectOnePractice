package com.test.mybatis.sqlSession;

import com.test.mybatis.configuration.Configuration;
import com.test.mybatis.executor.Executor;
import com.test.mybatis.xml.xmlEntity;

public class SqlSession {
	
	private Configuration configuration;
	private Executor executor;
	
	
	public SqlSession(Configuration configuration,Executor executor) {
		this.configuration = configuration;
		this.executor = executor;
	}
	
	public <T> T selectOne(String statementId,Object param) {
		xmlEntity entity= Configuration.sqlParseMap.get(statementId);
		return executor.selectOne(entity,param);		
	}
	
	public <T> T getMapper(Class clazz) {
		
		
		return configuration.getMapper(clazz,this);
	}

}
