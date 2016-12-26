package com.jslsolucoes.nginx.admin.log;

import org.quartz.Job;
import org.quartz.SimpleScheduleBuilder;

public interface CronTask extends Job {

	public SimpleScheduleBuilder frequency();

}
