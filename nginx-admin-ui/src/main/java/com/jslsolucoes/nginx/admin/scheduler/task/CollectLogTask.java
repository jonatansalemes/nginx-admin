package com.jslsolucoes.nginx.admin.scheduler.task;

import javax.inject.Inject;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.jslsolucoes.nginx.admin.repository.LogRepository;
import com.jslsolucoes.vraptor4.auth.annotation.Public;
import com.jslsolucoes.vraptor4.scheduler.SchedulerTask;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Public
public class CollectLogTask implements SchedulerTask {

	private LogRepository logRepository;
	private Result result;

	public CollectLogTask() {
		
	}

	@Inject
	public CollectLogTask(Result result, LogRepository logRepository) {
		this.result = result;
		this.logRepository = logRepository;

	}

	@Override
	public void execute() {
		logRepository.collect();
		this.result.use(Results.status()).ok();

	}

	@Override
	public Trigger frequency() {
		return TriggerBuilder.newTrigger().startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(35).repeatForever()).build();
	}
}
