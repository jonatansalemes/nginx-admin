package com.jslsolucoes.nginx.admin.log;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
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

	public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void build() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			Set<Bean<?>> tasks = beanManager.getBeans(CronTask.class);
			
			for (Bean<?> task : tasks) {
				Class<CronTask> taskClass = (Class<CronTask>) task.getBeanClass();
				CronTask cronTask = (CronTask) beanManager.getReference(task, task.getBeanClass(),
						beanManager.createCreationalContext(task));
				JobDetail job = JobBuilder.newJob(taskClass).build();
				Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(cronTask.frequency()).build();
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
