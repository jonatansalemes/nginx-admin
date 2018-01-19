package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("admin")
public class AdminController {

	private Result result;

	public AdminController() {
		
	}

	@Inject
	public AdminController(Result result) {
		this.result = result;
		
	}

	public void dashboard() {
		this.result.include("so", null);
		this.result.include("nginxDetail", null);
	}

	public void configure() {
		// configure logic
	}

	public void stop() {
		this.result.include("runtimeResult", null);
		this.result.redirectTo(this).dashboard();
	}

	public void reload() {
		this.result.include("runtimeResult", null);
		this.result.redirectTo(this).dashboard();
	}

	public void start() {
		this.result.include("runtimeResult", null);
		this.result.redirectTo(this).dashboard();
	}

	public void status() {
		this.result.include("runtimeResult", null);
		this.result.redirectTo(this).dashboard();
	}

	public void restart() {
		this.result.include("runtimeResult", null);
		this.result.redirectTo(this).dashboard();
	}

	public void testConfig() {
		this.result.include("runtimeResult", null);
		this.result.redirectTo(this).dashboard();
	}

}
