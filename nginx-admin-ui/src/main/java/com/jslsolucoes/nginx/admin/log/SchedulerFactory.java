/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.log;

import java.sql.SQLException;
import java.util.Set;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.jslsolucoes.nginx.admin.listener.ContextInitialized;

import br.com.caelum.vraptor.ioc.Container;

@ApplicationScoped
public class SchedulerFactory {

	@Inject
	private BeanManager beanManager;

	@Resource(lookup = "java:jboss/datasources/nginx-admin")
	private DataSource dataSource;
	
	@Inject 
	private Container container;

	@SuppressWarnings("unchecked")
	public void init(@Observes @ContextInitialized ServletContextEvent servletContextEvent) throws SchedulerException, SQLException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		Set<Bean<?>> tasks = beanManager.getBeans(CronTask.class);
		
		for (Bean<?> task : tasks) {
			Class<CronTask> taskClass = (Class<CronTask>) task.getBeanClass();
			CronTask cronTask = container.instanceFor(taskClass);
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("connection", dataSource.getConnection());
			JobDetail job = JobBuilder.newJob(taskClass).usingJobData(jobDataMap).build();
			Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(cronTask.frequency()).build();
			scheduler.scheduleJob(job, trigger);
		}
		scheduler.start();
	}
}
