package com.dev.mapper;

import java.util.List;
import java.util.Map;

import com.dev.entity.Emp;

public interface UserMapper {
	
	public Emp getUserInfoById(Emp emp);

	public List<Map<String, Object>> getAllUser();
}
