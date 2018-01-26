package com.jslsolucoes.nginx.admin.agent.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.auth.AuthHandler;
import com.jslsolucoes.nginx.admin.agent.config.Configuration;
import com.jslsolucoes.nginx.admin.agent.error.ErrorHandler;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxIndexResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxPingResponse;

@Path("/")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxIndexResource {
	
	
	private Configuration configuration;

	@Deprecated
	public NginxIndexResource() {
		
	}

	@Inject
	public NginxIndexResource(Configuration configuration) {
		this.configuration = configuration;
		this.configuration = configuration;
	}

	@GET
	public Response index() {
		return Response.ok(new NginxIndexResponse(configuration.getApplication().getVersion())).build();
	}
	
	@GET
	@Path("ping")
	public Response ping() {
		return Response.ok(new NginxPingResponse("pong")).build();
	}

}