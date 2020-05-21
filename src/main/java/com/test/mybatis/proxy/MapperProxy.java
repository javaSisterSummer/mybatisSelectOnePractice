package com.test.mybatis.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.test.mybatis.sqlSession.SqlSession;

public class MapperProxy implements InvocationHandler{
	
	private SqlSession sqlSession;
	
	public MapperProxy(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String nameSpace = method.getDeclaringClass().getName();
		String sqlId = method.getName();
		String statementId = nameSpace+"."+sqlId;
		return sqlSession.selectOne(statementId, args[0]);
	}
	

}
