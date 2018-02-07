package com.jslsolucoes.nginx.admin.scheduler.task;

import javax.inject.Inject;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.jslsolucoes.nginx.admin.repository.ErrorLogRepository;
import com.jslsolucoes.nginx.admin.ui.config.Configuration;
import com.jslsolucoes.vraptor4.auth.annotation.Public;
import com.jslsolucoes.vraptor4.scheduler.SchedulerTask;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Public
public class RotateErrorLogTask implements SchedulerTask {

	private ErrorLogRepository errorLogRepository;
	private Result result;
	private Configuration configuration;

	public RotateErrorLogTask() {

	}

	@Inject
	public RotateErrorLogTask(Result result, ErrorLogRepository errorLogRepository, Configuration configuration) {
		this.result = result;
		this.errorLogRepository = errorLogRepository;
		this.configuration = configuration;

	}

	@Override
	public void execute() {
		errorLogRepository.rotate();
		this.result.use(Results.status()).ok();
	}

	@Override
	public Trigger frequency() {
		return TriggerBuilder.newTrigger().startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInMinutes(configuration.getErrorLog().getRotate()).repeatForever()).build();
	}

}
