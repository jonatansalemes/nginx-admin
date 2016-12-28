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
package com.jslsolucoes.nginx.admin.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;
import com.jslsolucoes.nginx.admin.repository.SchedulerRepository;
import com.jslsolucoes.nginx.admin.scheduler.task.CollectLogTask;

@RequestScoped
public class SchedulerRepositoryImpl implements SchedulerRepository {
	
	private Scheduler scheduler;
	private ConfigurationRepository configurationRepository;

	public SchedulerRepositoryImpl() {
		
	}
	
	@Inject
	public SchedulerRepositoryImpl(Scheduler scheduler,ConfigurationRepository configurationRepository){
		this.scheduler = scheduler;
		this.configurationRepository = configurationRepository;
	}

	@Override
	public void scheduleJobs() throws SchedulerException {
		JobDetail job = JobBuilder.newJob(CollectLogTask.class)
				.usingJobData("url_base", configurationRepository.string(ConfigurationType.URL_BASE)).build();
		Trigger trigger = TriggerBuilder.newTrigger().startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(30).repeatForever()).build();
		scheduler.scheduleJob(job, trigger);
		scheduler.start();
	}

}
