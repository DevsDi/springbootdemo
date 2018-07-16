package com.dev.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public Object defaultErrorHandler(HttpServletRequest request,HttpServletResponse response, Object handler, Exception e) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		map.put("msg", e.getMessage());
		return map;

	}
	
	@ExceptionHandler(value = MyException.class)
	public Object handlerMyException(HttpServletRequest request,HttpServletResponse response, Object handler, MyException e) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		map.put("msg", e.getMsg());
		return map;

	}
}