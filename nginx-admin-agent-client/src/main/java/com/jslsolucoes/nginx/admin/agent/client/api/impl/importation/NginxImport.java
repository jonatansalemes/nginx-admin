package com.jslsolucoes.nginx.admin.agent.client.api.impl.importation;

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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxImportConfRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxImportConfResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxImport extends DefaultNginxAgentClientApi implements NginxAgentClientApi {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String authorizationKey;
	private final String conf;

	public NginxImport(ScheduledExecutorService scheduledExecutorService, String endpoint, String authorizationKey,
			String conf) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.authorizationKey = authorizationKey;
		this.conf = conf;
	}

	public CompletableFuture<NginxResponse> conf() {
		return CompletableFuture.supplyAsync(() -> {
			try (RestClient restClient = RestClient.build()) {
				NginxImportConfRequest nginxImportConfRequest = new NginxImportConfRequest(conf);
				Entity<NginxImportConfRequest> entity = Entity.entity(nginxImportConfRequest,
						MediaType.APPLICATION_JSON);
				WebTarget webTarget = restClient.target(endpoint);
				Response response = webTarget.path("import").path("conf").request()
						.header(HttpHeader.AUTHORIZATION, authorizationKey).post(entity);
				return responseFor(response, NginxImportConfResponse.class);
			} catch (Exception e) {
				return new NginxExceptionResponse(e);
			}
		}, scheduledExecutorService);
	}

}
