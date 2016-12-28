package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import org.quartz.SchedulerException;

import com.jslsolucoes.nginx.admin.annotation.Public;
import com.jslsolucoes.nginx.admin.repository.SchedulerRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Public
@Path("scheduler")
public class SchedulerController {

	private Result result;
	private SchedulerRepository schedulerRepository;

	public SchedulerController() {

	}

	@Inject
	public SchedulerController(Result result, SchedulerRepository schedulerRepository) {
		this.result = result;
		this.schedulerRepository = schedulerRepository;
	}

	
	public void scheduleJobs() throws SchedulerException {
		schedulerRepository.scheduleJobs();
		this.result.redirectTo(UserController.class).login();
	}
}
