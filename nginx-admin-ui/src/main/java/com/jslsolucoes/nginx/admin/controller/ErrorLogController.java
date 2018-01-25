package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.ErrorLogRepository;
import com.jslsolucoes.vaptor4.misc.pagination.Paginator;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("errorLog")
public class ErrorLogController {

	private Result result;
	private ErrorLogRepository errorLogRepository;
	private Paginator paginator;

	@Deprecated
	public ErrorLogController() {
		
	}

	@Inject
	public ErrorLogController(Result result, ErrorLogRepository errorLogRepository,Paginator paginator) {
		this.result = result;
		this.errorLogRepository = errorLogRepository;
		this.paginator = paginator;
	}

	@Path("list/{idNginx}")
	public void list(Long idNginx) {
		this.result.include("nginx",new Nginx(idNginx));
		this.result.include("totalResults",errorLogRepository.countFor(new Nginx(idNginx)));
		this.result.include("errorLogList", errorLogRepository.listAllFor(new Nginx(idNginx),paginator.start(), paginator.resultsPerPage()));
	}


}
