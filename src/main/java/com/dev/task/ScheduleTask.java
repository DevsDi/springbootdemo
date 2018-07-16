package com.dev.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {
	
	private Logger logger =LoggerFactory.getLogger(getClass());

//	@Scheduled(cron="0/1 * * * * ?")
	@Scheduled(fixedDelay=1000)
	public void testScheduled() {
	logger.info("date == "+new Date());
	}
}
