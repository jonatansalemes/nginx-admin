package com.jslsolucoes.nginx.admin.log;

import org.quartz.JobExecutionContext;
import org.quartz.SimpleScheduleBuilder;

public class LogCollector implements CronTask {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("executing job ...");
    }
    
    @Override
    public SimpleScheduleBuilder frequency() {
        return SimpleScheduleBuilder.simpleSchedule()
	              .withIntervalInSeconds(5)
	              .repeatForever();
    }
}