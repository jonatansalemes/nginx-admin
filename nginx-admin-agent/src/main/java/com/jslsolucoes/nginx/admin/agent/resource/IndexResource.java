package com.jslsolucoes.nginx.admin.agent.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.error.ErrorHandler;
import com.jslsolucoes.nginx.admin.agent.model.response.IndexResponse;


@Path("/")
@ErrorHandler
@Produces(MediaType.APPLICATION_JSON)
public class IndexResource {

	@GET
	public Response index() {
		return Response.ok(new IndexResponse("running...")).build();
	}

}