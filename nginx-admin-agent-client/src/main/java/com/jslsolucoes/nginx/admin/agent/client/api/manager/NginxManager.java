package com.jslsolucoes.nginx.admin.agent.client.api.manager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.jslsolucoes.nginx.admin.agent.client.RestClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxStartRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxStartResponse;

public class NginxManager implements NginxAgentClientApi {
	
	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String bin;

	public NginxManager(ScheduledExecutorService scheduledExecutorService,String endpoint,String bin) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.bin = bin;
	}
	
	public CompletableFuture<NginxStartResponse> start() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				NginxStartRequest nginxStartRequest = new NginxStartRequest(bin);
				Entity<NginxStartRequest> entity = Entity.entity(nginxStartRequest, MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				return webTarget.path("/manager/start").request().post(entity, NginxStartResponse.class);
			}
		}, scheduledExecutorService);
	}

}
