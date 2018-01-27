package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.ui.config.Configuration;
import com.jslsolucoes.vraptor4.auth.annotation.Public;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
public class AppController {

	private Configuration configuration;
	private Result result;
	private NginxRepository nginxRepository;

	@Deprecated
	public AppController() {

	}

	@Inject
	public AppController(Configuration configuration, Result result, NginxRepository nginxRepository) {
		this.configuration = configuration;
		this.result = result;
		this.nginxRepository = nginxRepository;
	}

	@Public
	@Path("/version")
	public void version() {
		this.result.use(Results.json()).from(configuration.getApplication().getVersion(), "version").serialize();
	}

	@Path(value = { "/", "/home" })
	public void home() {
		this.result.include("nginxList", nginxRepository.listAll());

	}

	@Path("/applySessionFor/{id}")
	public void applyFor(Long id) {
		this.result.include("nginx", nginxRepository.load(new Nginx(id)));
		this.result.forwardTo(this).home();
	}

	@Path("/welcome")
	public void welcome() {

	}
}
