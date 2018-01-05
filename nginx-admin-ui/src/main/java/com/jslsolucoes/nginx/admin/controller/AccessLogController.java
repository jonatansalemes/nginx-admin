package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

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

	public AccessLogController() {
		this(null, null,null);
	}

	@Inject
	public AccessLogController(Result result, AccessLogRepository accessLogRepository,Paginator paginator) {
		this.result = result;
		this.accessLogRepository = accessLogRepository;
		this.paginator = paginator;
	}

	
	public void list() {
		this.result.include("totalResults",accessLogRepository.count());
		this.result.include("accessLogList", accessLogRepository.listAll(paginator.start(), paginator.resultsPerPage()));
	}

}
