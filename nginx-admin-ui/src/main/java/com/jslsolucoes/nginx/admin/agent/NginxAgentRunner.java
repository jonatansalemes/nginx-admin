package com.jslsolucoes.nginx.admin.agent;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxCommandLineInterface;
import com.jslsolucoes.nginx.admin.agent.model.Endpoint;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.model.Configuration;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class NginxAgentRunner {

	private NginxAgentClient nginxAgentClient;
	private NginxRepository nginxRepository;
	private ConfigurationRepository configurationRepository;
	
	@Deprecated
	public NginxAgentRunner() {
		
	}
	
	@Inject
	public NginxAgentRunner(NginxAgentClient nginxAgentClient,NginxRepository nginxRepository,ConfigurationRepository configurationRepository) {
		this.nginxAgentClient = nginxAgentClient;
		this.nginxRepository = nginxRepository;
		this.configurationRepository = configurationRepository;
	}

	public NginxResponse stop(Long idNginx) {
		return nginxCommandLineInterface(idNginx).stop().join();
	}
	
	public NginxResponse createUpstream(Long idNginx,String uuid, String name,String strategy,List<Endpoint> endpoints) {
		Nginx nginx = nginx(idNginx);
		Configuration configuration = configuration(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.upstream())
				.withUuid(uuid)
				.withAuthorizationKey(nginx.getAuthorizationKey())
				.withEndpoint(nginx.getEndpoint())
				.withHome(configuration.getHome())
				.withName(name)
				.withStrategy(strategy)
				.withEndpoints(endpoints)
				.build().create().join();
	}

	public NginxResponse configure(Long idNginx, Integer gzip, String home, Integer maxPostSize) {
		Nginx nginx = nginx(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.configure()).withAuthorizationKey(nginx.getAuthorizationKey())
				.withEndpoint(nginx.getEndpoint()).withHome(home).withGzip((gzip == null ? false : true))
				.withMaxPostSize(maxPostSize).build().configure().join();
	}

	public NginxResponse nginxServerInfo(Long idNginx) {
		Nginx nginx = nginx(idNginx);
		Configuration configuration = configuration(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.nginxServerInfo())
				.withAuthorizationKey(nginx.getAuthorizationKey()).withEndpoint(nginx.getEndpoint())
				.withBin(configuration.getBin()).withHome(configuration.getHome()).build().info().join();
	}

	public NginxResponse nginxOperationalSystemInfo(Long idNginx) {
		Nginx nginx = nginx(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.operationalSystemInfo())
				.withAuthorizationKey(nginx.getAuthorizationKey()).withEndpoint(nginx.getEndpoint()).build()
				.operationalSystemInfo().join();
	}
	
	public NginxResponse reload(Long idNginx) {
		return nginxCommandLineInterface(idNginx).reload().join();
	}

	public NginxResponse start(Long idNginx) {
		return nginxCommandLineInterface(idNginx).start().join();
	}

	public NginxResponse status(Long idNginx) {
		return nginxCommandLineInterface(idNginx).status().join();
	}

	public NginxResponse restart(Long idNginx) {
		return nginxCommandLineInterface(idNginx).restart().join();
	}

	public NginxResponse testConfiguration(Long idNginx) {
		return nginxCommandLineInterface(idNginx).testConfiguration().join();
	}

	public NginxResponse killAll(Long idNginx) {
		return nginxCommandLineInterface(idNginx).killAll().join();
	}
	
	private NginxCommandLineInterface nginxCommandLineInterface(Long idNginx) {
		Nginx nginx = nginx(idNginx);
		Configuration configuration = configuration(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.commandLineInterface())
				.withAuthorizationKey(nginx.getAuthorizationKey()).withEndpoint(nginx.getEndpoint())
				.withBin(configuration.getBin()).withHome(configuration.getHome()).build();
	}

	private Nginx nginx(Long idNginx) {
		return nginxRepository.load(new Nginx(idNginx));
	}

	private Configuration configuration(Long idNginx) {
		return configurationRepository.loadFor(new Nginx(idNginx));
	}
}
