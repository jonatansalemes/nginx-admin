package com.jslsolucoes.nginx.admin.agent.client.api.impl.ping;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.client.RestClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.DefaultNginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.HttpHeader;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxPingResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxPing extends DefaultNginxAgentClientApi implements NginxAgentClientApi {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String authorizationKey;

	public NginxPing(ScheduledExecutorService scheduledExecutorService, String endpoint, String authorizationKey) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.authorizationKey = authorizationKey;
	}
	
	public CompletableFuture<NginxResponse> ping() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("/ping").request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey).get();
				return responseFor(response, NginxPingResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

}
