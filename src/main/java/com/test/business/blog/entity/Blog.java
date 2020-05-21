package com.test.business.blog.entity;

import java.io.Serializable;

public class Blog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String bid;
	
	private String name;
	
	private String authorId;

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return "Blog [bid=" + bid + ", name=" + name + ", authorId=" + authorId + "]";
	}
	
	

}
