package com.my.app.schedule.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.my.app.simple.job.SimpleJob;

@Service
public class ScheduleService {

	private final static Logger LOG = LoggerFactory.getLogger(ScheduleService.class);

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	private StdScheduler scheduler;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.scheduler = (StdScheduler) schedulerFactoryBean.getObject();

		// init1();

		try {
			init2();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	private void init1() throws ParseException, SchedulerException {
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

	private void init2() throws SchedulerException {
		JobKey jobKey = new JobKey("simpleJob");
		List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);

		if (triggers.isEmpty()) {
			TriggerKey triggerKey = new TriggerKey("simpleJob");
			JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity(jobKey).build();
			Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(triggerKey)
					.withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ?")).build();
			scheduler.scheduleJob(jobDetail, trigger);
		}
	}

	public void process() {
		LOG.info("Service process.");
	}

}
