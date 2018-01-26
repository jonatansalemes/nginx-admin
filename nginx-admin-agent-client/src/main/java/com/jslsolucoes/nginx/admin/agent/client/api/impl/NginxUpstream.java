package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import javax.enterprise.inject.Vetoed;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.client.RestClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.model.Endpoint;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxUpstreamCreateRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxUpstreamUpdateRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxUpstreamCreateResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxUpstreamDeleteResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxUpstreamReadResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxUpstreamUpdateResponse;

@Vetoed
public class NginxUpstream extends DefaultNginxAgentClientApi implements NginxAgentClientApi {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String authorizationKey;
	private final String name;
	private final String uuid;
	private final String strategy;
	private final List<Endpoint> endpoints;

	public NginxUpstream(ScheduledExecutorService scheduledExecutorService, String endpoint, 
			String authorizationKey,String uuid,String strategy,List<Endpoint> endpoints,
			String name) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.authorizationKey = authorizationKey;
		this.uuid = uuid;
		this.strategy = strategy;
		this.endpoints = endpoints;
		this.name = name;
	}

	
	public CompletableFuture<NginxResponse> update() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				NginxUpstreamUpdateRequest nginxUpstreamUpdateRequest = new NginxUpstreamUpdateRequest(name, strategy, endpoints);
				Entity<NginxUpstreamUpdateRequest> entity = Entity.entity(nginxUpstreamUpdateRequest,MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("upstream").path(uuid).request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey)
						.put(entity);
				return responseFor(response, NginxUpstreamUpdateResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

	public CompletableFuture<NginxResponse> create() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				NginxUpstreamCreateRequest nginxUpstreamCreateRequest = new NginxUpstreamCreateRequest(name, uuid, strategy, endpoints);
				Entity<NginxUpstreamCreateRequest> entity = Entity.entity(nginxUpstreamCreateRequest,MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("upstream").request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey)
						.post(entity);
				return responseFor(response, NginxUpstreamCreateResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}
	
	public CompletableFuture<NginxResponse> delete() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("upstream").path(uuid)
						.request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey)
						.delete();
				return responseFor(response, NginxUpstreamDeleteResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}
	
	public CompletableFuture<NginxResponse> read() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("upstream").path(uuid)
						.request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey)
						.get();
				return responseFor(response, NginxUpstreamReadResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

}
