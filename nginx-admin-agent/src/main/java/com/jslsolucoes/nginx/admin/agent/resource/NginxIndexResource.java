package com.jslsolucoes.nginx.admin.agent.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.auth.AuthHandler;
import com.jslsolucoes.nginx.admin.agent.error.ErrorHandler;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxIndexResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxPingResponse;

@Path("/")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxIndexResource {

	@GET
	public Response index() {
		return Response.ok(new NginxIndexResponse("running...")).build();
	}
	
	@GET
	@Path("ping")
	public Response ping() {
		return Response.ok(new NginxPingResponse("pong")).build();
	}

}