package com.dev.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
//配合@ServletComponentScan使用
@WebFilter(urlPatterns="/*",filterName="myFilter")
public class MyFilter implements Filter {

	private Logger logger =LoggerFactory.getLogger(getClass());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("====================init===========================");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		logger.info("====================doFilter===========================");
		
		HttpServletRequest httpServletRequest=(HttpServletRequest) request;
		HttpServletResponse response=(HttpServletResponse) res;
//		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		String user_agent=httpServletRequest.getHeader("user-agent");
		String ip=httpServletRequest.getRemoteAddr();
		String uri=httpServletRequest.getRequestURI();
		
		logger.info("user-agent:{},ip:{},uri{}",user_agent,ip,uri);
		chain.doFilter(httpServletRequest, response);
	}

	@Override
	public void destroy() {
		logger.info("====================destroy===========================");
	}

}
