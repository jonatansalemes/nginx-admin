package com.jslsolucoes.nginx.admin.controller;

import java.io.File;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.nginx.NginxConfiguration;
import com.jslsolucoes.nginx.admin.nginx.Runner;
import com.jslsolucoes.nginx.admin.os.OperationalSystem;
import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("admin")
public class AdminController {

	private Result result;
	private ConfigurationRepository configurationRepository;
	private Runner runner;

	public AdminController() {

	}

	@Inject
	public AdminController(Result result, ConfigurationRepository configurationRepository, Runner runner) {
		this.result = result;
		this.configurationRepository = configurationRepository;
		this.runner = runner;
	}

	public void dashboard() {
		this.result.include("so", OperationalSystem.info());
	}

	public void configure() {

	}

	public void stop() {

		this.result.include("runtimeResult",runner.stop(configuration()));
		this.result.redirectTo(this).dashboard();
	}

	public void start() {
		this.result.include("runtimeResult", runner.start(configuration()));
		this.result.redirectTo(this).dashboard();
	}

	public void status() {
		this.result.include("runtimeResult",runner.status(configuration()));
		this.result.redirectTo(this).dashboard();
	}

	public void restart() {
		this.result.include("runtimeResult",runner.restart(configuration()));
		this.result.redirectTo(this).dashboard();
	}

	private NginxConfiguration configuration() {
		return new NginxConfiguration(new File(configurationRepository.variable("NGINX_BIN")),
				new File(configurationRepository.variable("NGINX_CONF")));
	}

}
