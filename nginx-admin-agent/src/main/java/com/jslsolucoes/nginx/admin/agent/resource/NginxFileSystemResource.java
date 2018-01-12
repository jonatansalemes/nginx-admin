package com.jslsolucoes.nginx.admin.agent.resource;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.jslsolucoes.nginx.admin.agent.model.request.NginxConfigureRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxConfigureResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.runner.exec.NginxFileSystem;

@Path("fs")
@Produces(MediaType.APPLICATION_JSON)
public class NginxFileSystemResource {

	@Inject
	private NginxFileSystem nginxFileSystem;

	@POST
	@Path("configure")
	public void configure(NginxConfigureRequest nginxConfigureRequest, @Suspended AsyncResponse asyncResponse) {
		try {
			nginxFileSystem.configure(nginxConfigureRequest.getHome(), nginxConfigureRequest.getMaxPostSize(),
					nginxConfigureRequest.getGzip());
			asyncResponse.resume(Response.ok(new NginxConfigureResponse(nginxConfigureRequest.getHome(),
					nginxConfigureRequest.getMaxPostSize(), nginxConfigureRequest.getGzip())).build());
		} catch (Exception e) {
			asyncResponse.resume(
					Response.status(Status.INTERNAL_SERVER_ERROR).entity(new NginxExceptionResponse(e)).build());
		}
	}
}
