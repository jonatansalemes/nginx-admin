package com.jslsolucoes.nginx.admin.agent.client.api.impl.cli;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import javax.enterprise.inject.Vetoed;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.client.RestClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.DefaultNginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.HttpHeader;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxCommandLineInterfaceResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

@Vetoed
public class NginxCommandLineInterface extends DefaultNginxAgentClientApi implements NginxAgentClientApi {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String authorizationKey;

	public NginxCommandLineInterface(ScheduledExecutorService scheduledExecutorService, String endpoint,
			String authorizationKey) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.authorizationKey = authorizationKey;
	}

	public CompletableFuture<NginxResponse> start() {
		return request("/commandLineInterface/start");
	}

	public CompletableFuture<NginxResponse> killAll() {
		return request("/commandLineInterface/killAll");
	}

	public CompletableFuture<NginxResponse> status() {
		return request("/commandLineInterface/status");
	}

	public CompletableFuture<NginxResponse> stop() {
		return request("/commandLineInterface/stop");
	}

	public CompletableFuture<NginxResponse> restart() {
		return request("/commandLineInterface/restart");
	}

	public CompletableFuture<NginxResponse> reload() {
		return request("/commandLineInterface/reload");
	}

	public CompletableFuture<NginxResponse> version() {
		return request("/commandLineInterface/version");
	}

	public CompletableFuture<NginxResponse> testConfiguration() {
		return request("/commandLineInterface/testConfiguration");
	}

	public CompletableFuture<NginxResponse> request(String path) {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path(path).request().header(HttpHeader.AUTHORIZATION, authorizationKey)
						.get();
				return responseFor(response, NginxCommandLineInterfaceResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

}
