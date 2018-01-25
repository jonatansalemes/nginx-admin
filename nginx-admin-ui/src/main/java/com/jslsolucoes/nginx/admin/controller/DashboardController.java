package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxCommandLineInterface;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxOperationalSystemInfo;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("dashboard")
public class DashboardController {

	private Result result;
	private NginxAgentClient nginxAgentClient;
	private NginxRepository nginxRepository;
	private ConfigurationRepository configurationRepository;

	@Deprecated
	public DashboardController() {
		
	}

	@Inject
	public DashboardController(Result result,NginxAgentClient nginxAgentClient,NginxRepository nginxRepository,
			ConfigurationRepository configurationRepository) {
		this.result = result;
		this.nginxAgentClient = nginxAgentClient;
		this.nginxRepository = nginxRepository;
		this.configurationRepository = configurationRepository;
	}

	@Path("index/{id}")
	public void index(Long id) {
		this.result.include("nginxOperationalSystemDescriptionResponse", nginxOperationalSystemInfo(id).operationalSystemInfo().join());
		this.result.include("nginxServerDescriptionResponse", null);
		this.result.include("nginx",nginxRepository.load(new Nginx(id)));
		this.result.include("configuration",configurationRepository.loadFor(new Nginx(id)));
	}

	@Path("stop/{id}")
	public void stop(Long id) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxCommandLineInterface(id).stop().join());
		this.result.redirectTo(this).index(id);
	}

	@Path("reload/{id}")
	public void reload(Long id) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxCommandLineInterface(id).reload().join());
		this.result.redirectTo(this).index(id);
	}

	@Path("start/{id}")
	public void start(Long id) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxCommandLineInterface(id).start().join());
		this.result.redirectTo(this).index(id);
	}

	@Path("status/{id}")
	public void status(Long id) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxCommandLineInterface(id).status().join());
		this.result.redirectTo(this).index(id);
	}

	@Path("restart/{id}")
	public void restart(Long id) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxCommandLineInterface(id).restart().join());
		this.result.redirectTo(this).index(id);
	}

	@Path("testConfiguration/{id}")
	public void testConfiguration(Long id) {
		this.result.include("nginxCommandLineInterfaceResponse",nginxCommandLineInterface(id).testConfiguration().join());
		this.result.redirectTo(this).index(id);
	}
	
	private NginxOperationalSystemInfo nginxOperationalSystemInfo(Long id) {
		Nginx nginx = nginx(id);
		return nginxAgentClient.api(NginxAgentClientApis.operationalSystemInfo())
					.withAuthorizationKey(nginx.getAuthorizationKey())
					.withEndpoint(nginx.getEndpoint())
				.build();
	}
	
	private NginxCommandLineInterface nginxCommandLineInterface(Long id) {
		Nginx nginx = nginx(id);
		return nginxAgentClient.api(NginxAgentClientApis.commandLineInterface())
					.withAuthorizationKey(nginx.getAuthorizationKey())
					.withEndpoint(nginx.getEndpoint())
				.build();
	}
	
	private Nginx nginx(Long id) {
		return nginxRepository.load(new Nginx(id));
	}

}
