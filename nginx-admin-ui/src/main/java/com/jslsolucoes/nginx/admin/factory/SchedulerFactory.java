package com.jslsolucoes.nginx.admin.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerFactory {

	@ApplicationScoped
	@Produces
	public Scheduler getInstance() throws SchedulerException {
		return StdSchedulerFactory.getDefaultScheduler();
	}

	public void destroy(@Disposes Scheduler scheduler) throws SchedulerException {
		scheduler.shutdown();
	}
}
