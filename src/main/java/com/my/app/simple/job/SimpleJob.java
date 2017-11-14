package com.my.app.simple.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SimpleJob extends QuartzJobBean {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		LOG.debug("Simple job execute start.");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		LOG.debug("Simple job execute end.");
	}

}
