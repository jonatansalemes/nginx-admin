package com.jslsolucoes.nginx.admin.controller;

import java.util.Properties;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;
import com.jslsolucoes.nginx.admin.repository.impl.ConfigurationType;
import com.jslsolucoes.vaptor4.misc.annotation.ApplicationProperties;
import com.jslsolucoes.vraptor4.auth.annotation.Public;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
public class AppController {

	private Properties properties;
	private Result result;
	private ConfigurationRepository configurationRepository;

	public AppController() {
		this(null, null, null);
	}

	@Inject
	public AppController(@ApplicationProperties Properties properties, Result result,
			ConfigurationRepository configurationRepository) {
		this.properties = properties;
		this.result = result;
		this.configurationRepository = configurationRepository;
	}

	@Public
	@Path("/version")
	public void version() {
		this.result.use(Results.json()).from(properties.get("NGINX_ADMIN_VERSION"), "version").serialize();
	}

	@Path(value = { "/", "/home" })
	public void home() {

	}

	@Path("/app/edit")
	public void edit() {
		this.result.include("urlBase", configurationRepository.string(ConfigurationType.URL_BASE));
	}

	@Path("/app/update")
	@Post
	public void update(String urlBase) {
		configurationRepository.update(ConfigurationType.URL_BASE, urlBase);
		this.result.include("updated", true);
		this.result.redirectTo(this).edit();
	}

}
