package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import javax.enterprise.inject.Vetoed;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.jslsolucoes.nginx.admin.agent.client.RestClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxCommandLineInterfaceRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxCommandLineInterfaceResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

@Vetoed
public class NginxCommandLineInterface implements NginxAgentClientApi {
	
	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String bin;
	private final String home;
	private final String authorization;

	public NginxCommandLineInterface(ScheduledExecutorService scheduledExecutorService,String endpoint,String bin,String home,String authorization) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.bin = bin;
		this.home = home;
		this.authorization = authorization;
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
				NginxCommandLineInterfaceRequest nginxCommandLineInterfaceRequest = new NginxCommandLineInterfaceRequest(bin,home);
				Entity<NginxCommandLineInterfaceRequest> entity = Entity.entity(nginxCommandLineInterfaceRequest, MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path(path).request()
						.header(HttpHeader.AUTHORIZATION,authorization)
						.post(entity);
				if(response.getStatusInfo().equals(Status.OK)){
					return response.readEntity(NginxCommandLineInterfaceResponse.class);
				} if(response.getStatusInfo().equals(Status.FORBIDDEN)){
					return response.readEntity(NginxAuthenticationFailResponse.class);
				} else {
					return response.readEntity(NginxExceptionResponse.class);
				}
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

	

	

}
