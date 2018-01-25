package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import javax.enterprise.inject.Vetoed;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.client.RestClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxServerInfoRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxServerInfoResponse;

@Vetoed
public class NginxServerInfo extends DefaultNginxAgentClientApi implements NginxAgentClientApi {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String bin;
	private final String home;
	private final String authorizationKey;

	public NginxServerInfo(ScheduledExecutorService scheduledExecutorService, String endpoint, String bin,
			String home, String authorizationKey) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.bin = bin;
		this.home = home;
		this.authorizationKey = authorizationKey;
	}


	public CompletableFuture<NginxResponse> info() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				NginxServerInfoRequest nginxServerInfoRequest = new NginxServerInfoRequest(
						 home,bin);
				Entity<NginxServerInfoRequest> entity = Entity.entity(nginxServerInfoRequest,MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("/admin/nginxInfo").request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey)
						.post(entity);
				return responseFor(response, NginxServerInfoResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

}
