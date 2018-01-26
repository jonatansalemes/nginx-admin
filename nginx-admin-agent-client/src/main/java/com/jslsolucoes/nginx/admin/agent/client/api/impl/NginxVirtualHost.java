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
import com.jslsolucoes.nginx.admin.agent.model.Location;
import com.jslsolucoes.nginx.admin.agent.model.request.virtual.host.NginxVirtualHostCreateRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.virtual.host.NginxVirtualHostUpdateRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.virtual.host.NginxVirtualHostCreateResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.virtual.host.NginxVirtualHostDeleteResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.virtual.host.NginxVirtualHostReadResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.virtual.host.NginxVirtualHostUpdateResponse;

@Vetoed
public class NginxVirtualHost extends DefaultNginxAgentClientApi implements NginxAgentClientApi {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String authorizationKey;
	private final String uuid;
	private final Boolean https;
	private final String certificateUuid;
	private final String certificatePrivateKeyUuid;
	private final List<String> aliases;
	private final List<Location> locations;

	public NginxVirtualHost(ScheduledExecutorService scheduledExecutorService, String endpoint, 
			String authorizationKey,String uuid,Boolean https,String certificateUuid,
			String certificatePrivateKeyUuid,List<String> aliases,List<Location> locations) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.authorizationKey = authorizationKey;
		this.uuid = uuid;
		this.https = https;
		this.certificateUuid = certificateUuid;
		this.certificatePrivateKeyUuid = certificatePrivateKeyUuid;
		this.aliases = aliases;
		this.locations = locations;
	}

	
	public CompletableFuture<NginxResponse> update() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				NginxVirtualHostUpdateRequest nginxVirtualHostUpdateRequest = new NginxVirtualHostUpdateRequest(https, certificateUuid, certificatePrivateKeyUuid, aliases, locations);
				Entity<NginxVirtualHostUpdateRequest> entity = Entity.entity(nginxVirtualHostUpdateRequest,MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("virtualHost").path(uuid).request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey)
						.put(entity);
				return responseFor(response, NginxVirtualHostUpdateResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

	public CompletableFuture<NginxResponse> create() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				NginxVirtualHostCreateRequest nginxVirtualHostCreateRequest = new NginxVirtualHostCreateRequest(uuid, https, certificateUuid, certificatePrivateKeyUuid, aliases, locations);
				Entity<NginxVirtualHostCreateRequest> entity = Entity.entity(nginxVirtualHostCreateRequest,MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("virtualHost").request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey)
						.post(entity);
				return responseFor(response, NginxVirtualHostCreateResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}
	
	public CompletableFuture<NginxResponse> delete() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("virtualHost").path(uuid)
						.request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey)
						.delete();
				return responseFor(response, NginxVirtualHostDeleteResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}
	
	public CompletableFuture<NginxResponse> read() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("virtualHost").path(uuid)
						.request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey)
						.get();
				return responseFor(response, NginxVirtualHostReadResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

}
