package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.annotation.Public;
import com.jslsolucoes.nginx.admin.repository.LogRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("task")
@Public
public class TaskController {

	private Result result;
	private LogRepository logRepository;

	public TaskController() {

	}

	@Inject
	public TaskController(Result result, LogRepository logRepository) {
		this.result = result;
		this.logRepository = logRepository;

	}

	@Path("collect/log")
	public void collectLog() {
		logRepository.collect();
		this.result.use(Results.status()).ok();
	}
}
