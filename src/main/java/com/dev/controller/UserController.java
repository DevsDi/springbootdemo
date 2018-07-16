package com.dev.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dev.annotation.LoginUser;
import com.dev.annotation.NeedAuth;
import com.dev.entity.App;
import com.dev.entity.Emp;
import com.dev.exception.MyException;
import com.dev.mapper.RedisMapper;
import com.dev.service.Impl.UserServiceImpl;
import com.dev.task.AsyncTask;

@Controller
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private RedisMapper redisMapper;
    
	@Autowired
    private Environment environment;
	
//	@Autowired
//	private ArticleRepository articleRepository;

	private Logger logger =LoggerFactory.getLogger(getClass());
	
	@Value("${app.name}")
	private String appName;
	
	@Autowired
	private App app;
	
	
	@RequestMapping("/test")
	@ResponseBody
	public Object test() {
		try {
			int i=1/0;
		} catch (Exception e) {
			throw new MyException(1,"这是个自定义异常："+e.getMessage());
		}
		
		return app;
	}

	@RequestMapping(value="/out")
	@ResponseBody
	public Object out() {
		logger.info("-------->>-out---------" + appName);
		String deptTree=redisMapper.getValue("deptTree");
		logger.info(deptTree);
		return deptTree;
	}
	@RequestMapping(value="/getUserInfoById",method=RequestMethod.POST)
	@ResponseBody
	public Emp getUserInfoById(@RequestBody Map<String,String> paramMap) {
		Emp emp = new Emp();
		emp.setId(Integer.parseInt(paramMap.get("id")));
		return userService.getUserInfoById(emp);
	}

	@RequestMapping("/getAllUserList")
	@NeedAuth
	@ResponseBody
	public List<Map<String, Object>> getAllUser(@LoginUser Emp emp,Map<String, Object> requestMap) {
		System.out.println("==============="+emp);
		return userService.getAllUser();
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	@NeedAuth
	public String upload(@RequestParam("file") MultipartFile file,@LoginUser Emp emp) throws IOException{
		logger.info("---------upload---------" + file.getOriginalFilename());
		String path = environment.getProperty("upload_path") + File.separator;
		String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String pathName=path+fileName+suffix;
		logger.info("=======pathName======"+pathName);
		try {
			//方式一
//			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(path	+ fileName + suffix));
			//方式二
			file.transferTo(new File(pathName));
		} catch (Exception e) {
			return e.getMessage();
		}

		return "success";
	}
	
	@Autowired
	private AsyncTask asyncTask;
	
	@RequestMapping("async")
	@ResponseBody
	public String doTask() throws InterruptedException{
		long currentTimeMillis = System.currentTimeMillis();
		Future<String> task1 = asyncTask.task1();
		Future<String> task2 = asyncTask.task2();
		Future<String> task3 = asyncTask.task3();
		String result = null;
		for (;;) {
			if(task1.isDone() && task2.isDone() && task3.isDone()) {
				// 三个任务都调用完成，退出循环等待
				break;
			}
			Thread.sleep(1000);
		}
		long currentTimeMillis1 = System.currentTimeMillis();
		result = "task任务总耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms";
		return result;

	}
	
//	@GetMapping("save")
//	@ResponseBody
//	public Object save(){
//	
//		Article article = new Article();
//		article.setId(4);
//		article.setPv(123);
//		article.setContent("springboot整合elasticsearch");
//		article.setTitle("tt");
//		article.setSummary("搜索框架整合");
//		
//		articleRepository.save(article);
//	
//		return "success";
//	}
	
	
	
    /**
     * 使用QueryBuilder
     * termQuery("key", obj) 完全匹配
     * termsQuery("key", obj1, obj2..)   一次匹配多个值
     * matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
     * multiMatchQuery("text", "field1", "field2"..);  匹配多个字段, field有通配符忒行
     * matchAllQuery();         匹配所有文件
     */
//	@RequestMapping(value = "/search", method = RequestMethod.POST)
//	@ResponseBody
//	public Object search(@RequestBody Map<String, String> paramMap){
//		QueryBuilder queryBuilder = null;
//		if (paramMap.isEmpty()) {
//			 queryBuilder = QueryBuilders.matchAllQuery(); //搜索全部文档
//		}else {
//			 queryBuilder = QueryBuilders.matchQuery("title", paramMap.get("title")); 
//		}
//
//		Iterable<Article> list =  articleRepository.search(queryBuilder);
//		return list;
//	}
//	

}
