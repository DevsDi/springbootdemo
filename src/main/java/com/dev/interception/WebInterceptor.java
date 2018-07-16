package com.dev.interception;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dev.annotation.LoginUser;
import com.dev.annotation.NeedAuth;

/**
 * @ClassName: WebInterceptor
 * @Description: 拦截器
 * @author: wen.dai
 * @date: 2018年5月22日 下午6:46:08
 */
public class WebInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		// 在zuul中跨域已设置，代码中移除跨域设置
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,OPTIONS");
		response.setHeader(	"Access-Control-Allow-Headers",	"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,x-auth");

		if (!(handler instanceof HandlerMethod)) {// 如果对象不是handlermethod直接返回
			return true;
		}

		String token= request.getHeader("x-auth");
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		NeedAuth classNeedAuth = handlerMethod.getBeanType().getAnnotation(NeedAuth.class);
		NeedAuth methodNeedAuth=method.getAnnotation(NeedAuth.class);
		
		 if (!StringUtils.isEmpty(token) && (methodNeedAuth != null || classNeedAuth != null)) {
			 if (!StringUtils.isEmpty(token)) {
				 request.setAttribute("token", token);
				 return true;
			} else {
	              logger.error("非法token:{}" , token);
	              throw new RuntimeException("非法token:{}");
	          }
			
		  }

         if (StringUtils.isEmpty(token) && (classNeedAuth != null || methodNeedAuth != null)) {
             throw new RuntimeException("token不能为空:{}");
         }
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		String UserAgent = request.getHeader("User-Agent");
		logger.info("UserAgent===" + UserAgent);
		String remoteIp = request.getRemoteHost() + ","	+ request.getRequestURI();
		logger.info("remoteIp" + remoteIp);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception ex)
			throws Exception {}
}
