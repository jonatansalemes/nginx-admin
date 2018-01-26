package com.jslsolucoes.nginx.admin.agent.client.api.impl.configure;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.client.RestClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.DefaultNginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.HttpHeader;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxConfigureRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxConfigureResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxConfigure extends DefaultNginxAgentClientApi implements NginxAgentClientApi {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String authorizationKey;
	private final String endpoint;
	private final Integer maxPostSize;
	private final Boolean gzip;

	public NginxConfigure(ScheduledExecutorService scheduledExecutorService, String authorizationKey,
			String endpoint,  Integer maxPostSize, Boolean gzip) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.authorizationKey = authorizationKey;
		this.endpoint = endpoint;
		this.maxPostSize = maxPostSize;
		this.gzip = gzip;
	}

	public CompletableFuture<NginxResponse> configure() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				NginxConfigureRequest nginxConfigureRequest = new NginxConfigureRequest(maxPostSize, gzip);
				Entity<NginxConfigureRequest> entity = Entity.entity(nginxConfigureRequest,
						MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("/admin/configure").request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey).post(entity);
				return responseFor(response, NginxConfigureResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

}
