package com.jslsolucoes.nginx.admin.repository;

import org.quartz.SchedulerException;

public interface SchedulerRepository {

	public void scheduleJobs() throws SchedulerException;
}
