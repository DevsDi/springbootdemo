package com.dev.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.entity.Emp;
import com.dev.mapper.UserMapper;
import com.dev.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public Emp getUserInfoById(Emp emp) {
		return userMapper.getUserInfoById(emp);
	}

	public List<Map<String, Object>> getAllUser() {
		return userMapper.getAllUser();
	}

}
