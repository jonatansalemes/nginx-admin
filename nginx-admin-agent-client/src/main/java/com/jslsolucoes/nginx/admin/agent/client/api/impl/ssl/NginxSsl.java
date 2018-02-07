package com.jslsolucoes.nginx.admin.agent.client.api.impl.ssl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import javax.enterprise.inject.Vetoed;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.client.RestClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.DefaultNginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.HttpHeader;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.request.ssl.NginxSslCreateRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.ssl.NginxSslUpdateRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.ssl.NginxSslCreateResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.ssl.NginxSslDeleteResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.ssl.NginxSslReadResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.ssl.NginxSslUpdateResponse;

@Vetoed
public class NginxSsl extends DefaultNginxAgentClientApi implements NginxAgentClientApi {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String authorizationKey;
	private final String uuid;
	private final FileObject fileObject;

	public NginxSsl(ScheduledExecutorService scheduledExecutorService, String endpoint, String authorizationKey,
			String uuid, FileObject fileObject) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.authorizationKey = authorizationKey;
		this.uuid = uuid;
		this.fileObject = fileObject;
	}

	public CompletableFuture<NginxResponse> update() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				NginxSslUpdateRequest nginxSslUpdateRequest = new NginxSslUpdateRequest(fileObject);
				Entity<NginxSslUpdateRequest> entity = Entity.entity(nginxSslUpdateRequest, MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("virtualHost").path(uuid).request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey).put(entity);
				return responseFor(response, NginxSslUpdateResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

	public CompletableFuture<NginxResponse> create() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				NginxSslCreateRequest nginxSslCreateRequest = new NginxSslCreateRequest(uuid, fileObject);
				Entity<NginxSslCreateRequest> entity = Entity.entity(nginxSslCreateRequest, MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("ssl").request().header(HttpHeader.AUTHORIZATION, authorizationKey)
						.post(entity);
				return responseFor(response, NginxSslCreateResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

	public CompletableFuture<NginxResponse> delete() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("ssl").path(uuid).request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey).delete();
				return responseFor(response, NginxSslDeleteResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

	public CompletableFuture<NginxResponse> read() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("ssl").path(uuid).request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey).get();
				return responseFor(response, NginxSslReadResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

}
