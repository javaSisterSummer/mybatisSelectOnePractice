package com.test;

import com.test.business.blog.dto.BlogMapper;
import com.test.business.blog.entity.Blog;
import com.test.mybatis.configuration.Configuration;
import com.test.mybatis.executor.Executor;
import com.test.mybatis.sqlSession.SqlSession;

public class MyBatisTest {
	
	
	public static void main(String[] args) {
		
		SqlSession session = new SqlSession(new Configuration(),new Executor());
		BlogMapper mapper = session.getMapper(BlogMapper.class);
		Blog blog = mapper.selectBlogById("1");
		System.out.println(blog.toString());
	}

}
