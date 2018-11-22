package com.my.app.schedule.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.impl.StdScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.my.app.simple.job.SimpleJob;

@Service
public class ScheduleService {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@PostConstruct
	public void init() throws Exception {
		StdScheduler scheduler = (StdScheduler) schedulerFactoryBean.getObject();

		JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
		jobDetailFactoryBean.setJobClass(SimpleJob.class);
		jobDetailFactoryBean.setBeanName("simpleJob.class");
		Map<String, String> jobDataAsMap = new HashMap<>();
		jobDataAsMap.put("test", "test123");
		jobDetailFactoryBean.setJobDataAsMap(jobDataAsMap);
		jobDetailFactoryBean.afterPropertiesSet();

		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setName("simpleJobTrigger");
		cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
		cronTriggerFactoryBean.setCronExpression("0/3 * * * * ?");
		cronTriggerFactoryBean.afterPropertiesSet();

		scheduler.scheduleJob(jobDetailFactoryBean.getObject(), cronTriggerFactoryBean.getObject());
	}

}
