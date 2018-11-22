package com.my.app.simple.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class SimpleJob extends QuartzJobBean {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleJob.class);

	@Autowired
	private ApplicationContext applicationContext;

	public SimpleJob() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		LOG.debug("Simple job execute start. " + applicationContext.getDisplayName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		LOG.debug("Simple job execute end.");
	}

}
