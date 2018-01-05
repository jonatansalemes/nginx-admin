package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.repository.ErrorLogRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("errorLog")
public class ErrorLogController {

	private Result result;
	private ErrorLogRepository errorLogRepository;

	public ErrorLogController() {
		this(null, null);
	}

	@Inject
	public ErrorLogController(Result result, ErrorLogRepository errorLogRepository) {
		this.result = result;
		this.errorLogRepository = errorLogRepository;
	}

	public void list() {
		this.result.include("errorLogContent",errorLogRepository.content());
	}

}
