package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.client.RestClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.error.log.NginxErrorLogCollectResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.error.log.NginxErrorLogRotateResponse;

public class NginxErrorLog extends DefaultNginxAgentClientApi implements NginxAgentClientApi {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String authorizationKey;

	public NginxErrorLog(ScheduledExecutorService scheduledExecutorService, String endpoint, String authorizationKey) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.authorizationKey = authorizationKey;
	}
	
	public CompletableFuture<NginxResponse> collect() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("errorLog").path("collect").request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey).get();
				return responseFor(response, NginxErrorLogCollectResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}
	
	public CompletableFuture<NginxResponse> rotate() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("errorLog").path("rotate").request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey).get();
				return responseFor(response, NginxErrorLogRotateResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

}
