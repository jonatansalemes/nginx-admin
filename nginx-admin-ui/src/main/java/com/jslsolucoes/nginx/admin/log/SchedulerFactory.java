package com.jslsolucoes.nginx.admin.log;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

@ApplicationScoped
public class SchedulerFactory {

	@Inject
	private BeanManager beanManager;
	
	private Scheduler scheduler;
	
	public SchedulerFactory() {
		
	}

	@PostConstruct
	public void build() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			Set<Bean<?>> tasks = beanManager.getBeans(CronTask.class);
			for (Bean<?> task : tasks) {
				JobDetail job = JobBuilder.newJob(((CronTask) task).getClass()).build();
				Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(((CronTask) task).frequency())
						.build();
				scheduler.scheduleJob(job, trigger);
			}
			scheduler.start();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@PreDestroy
	public void destroy() {
		try {
			scheduler.shutdown();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
	}
}
