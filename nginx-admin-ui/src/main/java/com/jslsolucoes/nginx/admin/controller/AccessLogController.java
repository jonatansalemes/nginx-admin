package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.AccessLogRepository;
import com.jslsolucoes.vaptor4.misc.pagination.Paginator;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("accessLog")
public class AccessLogController {

	private Result result;
	private AccessLogRepository accessLogRepository;
	private Paginator paginator;

	@Deprecated
	public AccessLogController() {
		
	}

	@Inject
	public AccessLogController(Result result, AccessLogRepository accessLogRepository,Paginator paginator) {
		this.result = result;
		this.accessLogRepository = accessLogRepository;
		this.paginator = paginator;
	}

	
	@Path("list/{idNginx}")
	public void list(Long idNginx) {
		this.result.include("totalResults",accessLogRepository.countFor(new Nginx(idNginx)));
		this.result.include("accessLogList", accessLogRepository.listAllFor(new Nginx(idNginx),paginator.start(), paginator.resultsPerPage()));
	}

}
