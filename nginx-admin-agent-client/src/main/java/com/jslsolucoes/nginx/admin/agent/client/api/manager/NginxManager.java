package com.jslsolucoes.nginx.admin.agent.client.api.manager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.jslsolucoes.nginx.admin.agent.client.RestClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxCliRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxCliResponse;

public class NginxManager implements NginxAgentClientApi {
	
	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String bin;

	public NginxManager(ScheduledExecutorService scheduledExecutorService,String endpoint,String bin) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.bin = bin;
	}
	
	public CompletableFuture<NginxCliResponse> start() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				NginxCliRequest nginxStartRequest = new NginxCliRequest(bin);
				Entity<NginxCliRequest> entity = Entity.entity(nginxStartRequest, MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				return webTarget.path("/cli/start").request().post(entity, NginxCliResponse.class);
			}
		}, scheduledExecutorService);
	}

}
