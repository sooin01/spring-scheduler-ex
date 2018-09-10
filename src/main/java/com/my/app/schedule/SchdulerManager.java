package com.my.app.schedule;

import java.util.UUID;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.my.app.schedule.job.FirstJob;

public class SchdulerManager {

	public void run() throws Exception {
		String name = UUID.randomUUID().toString() + "_myJob";
		String group = "myGroup";
		JobKey jobKey = JobKey.jobKey(name, group);
		TriggerKey triggerKey = TriggerKey.triggerKey(name, group);

		JobDetail jobDetail = JobBuilder.newJob(FirstJob.class).withIdentity(jobKey).build();
		Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(triggerKey)
				.withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?")).build();

		StdSchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		scheduler.start();
		scheduler.scheduleJob(jobDetail, trigger);

		Thread.sleep(2000);

		boolean deleteJob = scheduler.deleteJob(jobKey);
		System.out.println("deleteJob: " + deleteJob);
		boolean unscheduleJob = scheduler.unscheduleJob(triggerKey);
		System.out.println("unscheduleJob: " + unscheduleJob);

		scheduler.shutdown();
	}

	public static void main(String[] args) throws Exception {
		SchdulerManager schdulerManager = new SchdulerManager();
		schdulerManager.run();
	}

}
