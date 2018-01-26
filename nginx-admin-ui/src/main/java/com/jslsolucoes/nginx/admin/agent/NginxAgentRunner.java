package com.jslsolucoes.nginx.admin.agent;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxCommandLineInterface;
import com.jslsolucoes.nginx.admin.agent.model.Endpoint;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class NginxAgentRunner {

	private NginxAgentClient nginxAgentClient;
	private NginxRepository nginxRepository;
	
	@Deprecated
	public NginxAgentRunner() {
		
	}
	
	@Inject
	public NginxAgentRunner(NginxAgentClient nginxAgentClient,NginxRepository nginxRepository) {
		this.nginxAgentClient = nginxAgentClient;
		this.nginxRepository = nginxRepository;
	}

	public NginxResponse stop(Long idNginx) {
		return nginxCommandLineInterface(idNginx).stop().join();
	}
	
	public NginxResponse createUpstream(Long idNginx,String uuid, String name,String strategy,List<Endpoint> endpoints) {
		Nginx nginx = nginx(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.upstream())
				.withUuid(uuid)
				.withAuthorizationKey(nginx.getAuthorizationKey())
				.withEndpoint(nginx.getEndpoint())
				.withName(name)
				.withStrategy(strategy)
				.withEndpoints(endpoints)
				.build().create().join();
	}
	
	public NginxResponse updateUpstream(Long idNginx,String uuid, String name,String strategy,List<Endpoint> endpoints) {
		Nginx nginx = nginx(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.upstream())
				.withUuid(uuid)
				.withAuthorizationKey(nginx.getAuthorizationKey())
				.withEndpoint(nginx.getEndpoint())
				.withName(name)
				.withStrategy(strategy)
				.withEndpoints(endpoints)
				.build().update().join();
	}
	
	public NginxResponse deleteUpstream(Long idNginx,String uuid) {
		Nginx nginx = nginx(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.upstream())
				.withUuid(uuid)
				.withAuthorizationKey(nginx.getAuthorizationKey())
				.withEndpoint(nginx.getEndpoint())
				.build().delete().join();
	}

	public NginxResponse configure(Long idNginx, Integer gzip, Integer maxPostSize) {
		Nginx nginx = nginx(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.configure()).withAuthorizationKey(nginx.getAuthorizationKey())
				.withEndpoint(nginx.getEndpoint()).withGzip((gzip == null ? false : true))
				.withMaxPostSize(maxPostSize).build().configure().join();
	}
	
	public NginxResponse statusForNginx(Long idNginx) {
		Nginx nginx = nginx(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.status())
				.withAuthorizationKey(nginx.getAuthorizationKey()).withEndpoint(nginx.getEndpoint())
				.build().status().join();
	}

	public NginxResponse info(Long idNginx) {
		Nginx nginx = nginx(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.info())
				.withAuthorizationKey(nginx.getAuthorizationKey()).withEndpoint(nginx.getEndpoint())
				.build().info().join();
	}

	public NginxResponse os(Long idNginx) {
		Nginx nginx = nginx(idNginx);
		return nginxAgentClient.api(NginxAgentClientApis.os())
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
		return nginxAgentClient.api(NginxAgentClientApis.cli())
				.withAuthorizationKey(nginx.getAuthorizationKey()).withEndpoint(nginx.getEndpoint())
				.build();
	}

	private Nginx nginx(Long idNginx) {
		return nginxRepository.load(new Nginx(idNginx));
	}
}
