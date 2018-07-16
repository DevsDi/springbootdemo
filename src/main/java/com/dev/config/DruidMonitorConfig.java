package com.dev.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidMonitorConfig {

    /**
     * 注册ServletRegistrationBean
     * @return
     */
    @Bean
    public ServletRegistrationBean registrationBean() {
		ServletRegistrationBean bean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
		Map<String, String> paramMap=new HashMap<String, String>();
		paramMap.put("loginUsername", "admin"); 
		paramMap.put("loginPassword", "123456"); 
		paramMap.put("allow", ""); 
		paramMap.put("deny", "172.16.6.27"); 
		bean.setInitParameters(paramMap);
		bean.setOrder(1);
		return bean;
    }

    /**
     * 注册FilterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
    	FilterRegistrationBean bean=new FilterRegistrationBean();
		bean.setFilter(new WebStatFilter());
		bean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.

		Map<String, String> paramMap=new HashMap<String, String>();
        paramMap.put("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"); 
		bean.setInitParameters(paramMap);
		bean.setOrder(2);
        return bean;
    }

}