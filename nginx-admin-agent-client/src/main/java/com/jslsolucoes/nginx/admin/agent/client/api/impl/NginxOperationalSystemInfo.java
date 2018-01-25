package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.client.RestClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxOperationalSystemInfoResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxOperationalSystemInfo extends DefaultNginxAgentClientApi implements NginxAgentClientApi {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String authorizationKey;
	private String endpoint;

	public NginxOperationalSystemInfo(ScheduledExecutorService scheduledExecutorService, String authorizationKey,
			String endpoint) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.authorizationKey = authorizationKey;
		this.endpoint = endpoint;
	}

	public CompletableFuture<NginxResponse> operationalSystemInfo() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("/admin/operationalSystemInfo").request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey).get();
				return responseFor(response, NginxOperationalSystemInfoResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

}
