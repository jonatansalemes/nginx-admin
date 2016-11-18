package com.jslsolucoes.nginx.admin.controller;

import java.lang.management.ManagementFactory;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;
import com.jslsolucoes.nginx.admin.util.RuntimeUtils;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("admin")
public class AdminController {

	private Result result;
	private ConfigurationRepository configurationRepository;

	public AdminController() {
		
	}
	
	@Inject
	public AdminController(Result result,ConfigurationRepository configurationRepository) {
		this.result = result;
		this.configurationRepository = configurationRepository;
		
	}
	
	public void dashboard(){
		 this.result.include("so",ManagementFactory.getOperatingSystemMXBean());
	}
	
	public void configure(){
		
	}
	
	public void stop() {
		this.result.include("runtimeResult",RuntimeUtils.command(configurationRepository.variable("NGINX_BIN") + " stop"));
		this.result.redirectTo(this).dashboard();
	}
	
	public void start(){
		this.result.include("runtimeResult",RuntimeUtils.command(configurationRepository.variable("NGINX_BIN") + " start"));
		this.result.redirectTo(this).dashboard();
	}
	
	public void status(){
		this.result.include("runtimeResult",RuntimeUtils.command(configurationRepository.variable("NGINX_BIN") + " status"));
		this.result.redirectTo(this).dashboard();
	}
	
	public void restart() {
		this.result.include("runtimeResult",RuntimeUtils.command(configurationRepository.variable("NGINX_BIN") +" restart"));
		this.result.redirectTo(this).dashboard();
	}
	
}
