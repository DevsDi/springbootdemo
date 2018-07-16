package com.dev.service;

import java.util.List;
import java.util.Map;

import com.dev.entity.Emp;

public interface UserService {
	
	public Emp getUserInfoById(Emp emp);

	public List<Map<String, Object>> getAllUser();
}
