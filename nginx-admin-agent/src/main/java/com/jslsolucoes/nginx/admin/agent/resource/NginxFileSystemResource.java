package com.jslsolucoes.nginx.admin.agent.resource;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.auth.AuthHandler;
import com.jslsolucoes.nginx.admin.agent.error.ErrorHandler;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxConfigureRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxFileSystemPermissionRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxConfigureResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxFileSystemPermissionResponse;
import com.jslsolucoes.nginx.admin.agent.runner.exec.NginxFileSystem;

@Path("fs")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxFileSystemResource {

	@Inject
	private NginxFileSystem nginxFileSystem;

	@POST
	@Path("configure")
	public void configure(NginxConfigureRequest nginxConfigureRequest, @Suspended AsyncResponse asyncResponse) {
		nginxFileSystem.configure(nginxConfigureRequest.getHome(), nginxConfigureRequest.getMaxPostSize(),
				nginxConfigureRequest.getGzip());
		asyncResponse.resume(Response.ok(new NginxConfigureResponse(nginxConfigureRequest.getHome(),
				nginxConfigureRequest.getMaxPostSize(), nginxConfigureRequest.getGzip())).build());
	}

	@POST
	@Path("permission")
	public void permission(NginxFileSystemPermissionRequest nginxFileSystemPermissionRequest,
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxFileSystemPermissionResponse(
								nginxFileSystem.hasPermissionToWrite(nginxFileSystemPermissionRequest.getPath())))
						.build());
	}
}
