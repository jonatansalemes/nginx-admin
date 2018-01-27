package com.jslsolucoes.nginx.admin.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClient;
import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClientBuilder;

@ApplicationScoped
public class NginxAgentClientFactory {

	@Produces
	public NginxAgentClient produces() {
		return NginxAgentClientBuilder.newBuilder().build();
	}

	public void disposes(@Disposes NginxAgentClient nginxAgentClient) {
		nginxAgentClient.close();
	}
}
