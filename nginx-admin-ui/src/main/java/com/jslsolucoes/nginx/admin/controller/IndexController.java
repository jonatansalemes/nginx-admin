package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.annotation.Public;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Public
public class IndexController {

	private Result result;

	public IndexController() {
	}

	@Inject
	public IndexController(Result result) {
		this.result = result;
	}

	@Path("/favicon.ico")
	public void favicon() {
		this.result.forwardTo("/WEB-INF/jsp/index/favicon.ico");
	}

	@Path("/robots.txt")
	public void robots() {
		this.result.forwardTo("/WEB-INF/jsp/index/robots.txt");
	}
}
