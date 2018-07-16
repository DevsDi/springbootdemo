package com.dev.interception;

import java.lang.annotation.Annotation;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.dev.annotation.LoginUser;
import com.dev.entity.Emp;

@Component
public class MyHandlerMethodArgumentResolver implements
		HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		Class<?> clazz = methodParameter.getParameterType();//参数类型
		LoginUser loginUser = methodParameter.getParameterAnnotation(LoginUser.class);//注解类型
		if (loginUser != null) {
			if (clazz.isAssignableFrom(Emp.class)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter,
			ModelAndViewContainer mavContainer,
			NativeWebRequest nativeWebRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		String token = (String) nativeWebRequest.getAttribute("token",RequestAttributes.SCOPE_REQUEST);
		if (!StringUtils.isEmpty(token)) {

			    Class<?> clazz = methodParameter.getParameterType();
	            if (clazz.isAssignableFrom(Emp.class)) {
				Emp emp = new Emp();
				emp.setUser_code("E018725");
				emp.setUser_name("秋秋堂");
				return emp;
			}
	            throw new RuntimeException("token失效或者token不正常");
        }
		
		  throw new MissingServletRequestPartException("@LoginUser注解 参数错误");
	}
}
