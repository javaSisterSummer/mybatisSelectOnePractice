package com.test.business.blog.dto;

import com.test.business.blog.entity.Blog;

public interface BlogMapper {
	
	
	Blog selectBlogById(String bid);

}
