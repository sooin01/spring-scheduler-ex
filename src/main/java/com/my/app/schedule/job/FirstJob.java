package com.my.app.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

public class FirstJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Trigger trigger = context.getTrigger();
		System.out.println(trigger.getKey());
	}

}
