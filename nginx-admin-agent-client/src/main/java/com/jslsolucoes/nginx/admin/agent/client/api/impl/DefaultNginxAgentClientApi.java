package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApi;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class DefaultNginxAgentClientApi implements NginxAgentClientApi {

	protected <T extends NginxResponse> NginxResponse responseFor(Response response,Class<T> clazz) {
		if (response.getStatusInfo().equals(Status.OK) || response.getStatusInfo().equals(Status.CREATED)) {
			return response.readEntity(clazz);
		} else if (response.getStatusInfo().equals(Status.FORBIDDEN)) {
			return response.readEntity(NginxAuthenticationFailResponse.class);
		} else {
			return response.readEntity(NginxExceptionResponse.class);
		}
	}
}
