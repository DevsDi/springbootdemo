package com.dev.entity;


public class Emp {
	private Integer id;
	
	private String user_code;
	
	private String user_name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Override
	public String toString() {
		return "Emp [id=" + id + ", user_code=" + user_code + ", user_name="
				+ user_name + "]";
	}
	
}
