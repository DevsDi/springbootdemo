package com.dev;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@ServletComponentScan
@SpringBootApplication
//加载额外的配置文件
@PropertySource("classpath:config.properties")
//加载mybatis的mapper接口
@MapperScan("com.dev.mapper")
// 外部Tomcat war启动   extends SpringBootServletInitializer
//@EnableScheduling
@EnableAsync
//@EnableAdminServer
public class DemoApplication{
	
	
// 外部Tomcat war启动  
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		System.out.println("================war启动==========================");
//		return builder.sources(DemoApplication.class);
//	}

	//将该对象加入到容器中
	@Bean
	//数据源配置文件字段自动注入到对象中
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource druid(){
		DruidDataSource ds=new DruidDataSource();
		return ds;
	}
	

	@Bean
	//配置监控页面控制台
	public ServletRegistrationBean<StatViewServlet> setStatViewServlet() {
		ServletRegistrationBean<StatViewServlet> bean=new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(),"/druid/*");
		Map<String, String> paramMap=new HashMap<String, String>();
		paramMap.put("loginUsername", "admin"); 
		paramMap.put("loginPassword", "123456"); 
		paramMap.put("allow", ""); 
		bean.setInitParameters(paramMap);
		bean.setOrder(1);
		return bean;
	}
	
	@Bean
	//用于监控获取应用的程序，Filter用于收集数据，Servlet用于展示数据
	public FilterRegistrationBean<Filter> setWebStatFilter() {
		FilterRegistrationBean<Filter> bean=new FilterRegistrationBean<Filter>();
		bean.setFilter(new WebStatFilter());
		bean.addUrlPatterns("/*");
		
		Map<String, String> paramMap=new HashMap<String, String>();
		paramMap.put("exclusions", "*.js,*.css,/druid/*"); 
		bean.setInitParameters(paramMap);
		bean.setOrder(2);
		return bean;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
