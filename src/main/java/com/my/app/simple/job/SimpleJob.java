package com.my.app.simple.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.my.app.schedule.service.ScheduleService;

public class SimpleJob extends QuartzJobBean {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleJob.class);

	@Autowired
	private ScheduleService scheduleService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		LOG.debug("Simple job execute start.");

		scheduleService.process();

		LOG.debug("Simple job execute end.");
	}

}
