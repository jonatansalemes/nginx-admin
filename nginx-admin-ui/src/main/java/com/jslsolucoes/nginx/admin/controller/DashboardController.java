package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.agent.NginxAgentRunner;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("dashboard")
public class DashboardController {

	private Result result;
	private NginxAgentRunner nginxAgentRunner;
	private NginxRepository nginxRepository;
	@Deprecated
	public DashboardController() {
		
	}

	@Inject
	public DashboardController(Result result,NginxRepository nginxRepository,NginxAgentRunner nginxAgentRunner) {
		this.result = result;
		this.nginxRepository = nginxRepository;
		this.nginxAgentRunner = nginxAgentRunner;
	}

	@Path("index/{idNginx}")
	public void index(Long idNginx) {
		this.result.include("nginxOperationalSystemInfoResponse", nginxAgentRunner.os(idNginx));
		this.result.include("nginxServerInfoResponse", nginxAgentRunner.info(idNginx));
		this.result.include("nginxStatusResponse", nginxAgentRunner.statusForNginx(idNginx));
		this.result.include("nginx",nginxRepository.load(new Nginx(idNginx)));
	}

	@Path("stop/{idNginx}")
	public void stop(Long idNginx) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxAgentRunner.stop(idNginx));
		this.result.redirectTo(this).index(idNginx);
	}

	@Path("reload/{idNginx}")
	public void reload(Long idNginx) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxAgentRunner.reload(idNginx));
		this.result.redirectTo(this).index(idNginx);
	}

	@Path("start/{idNginx}")
	public void start(Long idNginx) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxAgentRunner.start(idNginx));
		this.result.redirectTo(this).index(idNginx);
	}
	
	@Path("killAll/{idNginx}")
	public void killAll(Long idNginx) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxAgentRunner.killAll(idNginx));
		this.result.redirectTo(this).index(idNginx);
	}

	@Path("status/{idNginx}")
	public void status(Long idNginx) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxAgentRunner.status(idNginx));
		this.result.redirectTo(this).index(idNginx);
	}

	@Path("restart/{idNginx}")
	public void restart(Long idNginx) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxAgentRunner.restart(idNginx));
		this.result.redirectTo(this).index(idNginx);
	}

	@Path("testConfiguration/{idNginx}")
	public void testConfiguration(Long idNginx) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxAgentRunner.testConfiguration(idNginx));
		this.result.redirectTo(this).index(idNginx);
	}
	
	
}
