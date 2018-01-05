package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.nginx.detail.NginxDetail;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;
import com.jslsolucoes.nginx.admin.os.OperationalSystem;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("admin")
public class AdminController {

	private Result result;
	private Runner runner;
	private NginxDetail nginxDetail;

	public AdminController() {
		this(null, null, null);
	}

	@Inject
	public AdminController(Result result, Runner runner, NginxDetail nginxDetail) {
		this.result = result;
		this.runner = runner;
		this.nginxDetail = nginxDetail;

	}

	public void dashboard() {
		this.result.include("so", OperationalSystem.info());
		this.result.include("nginxDetail", nginxDetail);
	}

	public void configure() {
		// configure logic
	}

	public void stop() {
		this.result.include("runtimeResult", runner.stop());
		this.result.redirectTo(this).dashboard();
	}

	public void reload() {
		this.result.include("runtimeResult", runner.reload());
		this.result.redirectTo(this).dashboard();
	}

	public void start() {
		this.result.include("runtimeResult", runner.start());
		this.result.redirectTo(this).dashboard();
	}

	public void status() {
		this.result.include("runtimeResult", runner.status());
		this.result.redirectTo(this).dashboard();
	}

	public void restart() {
		this.result.include("runtimeResult", runner.restart());
		this.result.redirectTo(this).dashboard();
	}

	public void testConfig() {
		this.result.include("runtimeResult", runner.testConfig());
		this.result.redirectTo(this).dashboard();
	}

}
