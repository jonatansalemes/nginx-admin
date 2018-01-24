package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("configuration")
public class ConfigurationController {

	private Result result;
	private ConfigurationRepository configurationRepository;

	@Deprecated
	public ConfigurationController() {
		
	}

	@Inject
	public ConfigurationController(Result result, ConfigurationRepository configurationRepository) {
		this.result = result;
		this.configurationRepository = configurationRepository;
	}
	
	@Path("form/{idNginx}")
	public void form(Long idNginx) {
		this.result.include("nginx",new Nginx(idNginx));
	}
	
	@Path("edit/{idNginx}")
	public void edit(Long idNginx) {
		this.result.include("configuration",configurationRepository.loadFor(new Nginx(idNginx)));
		this.result.forwardTo(this).form(idNginx);
	}
	
}
