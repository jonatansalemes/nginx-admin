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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxLogCollectRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxLogRotateRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxConfigureResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxFileSystemPermissionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxLogCollectResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxLogRotateResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxFileSystemResourceImpl;

@Path("fs")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxFileSystemResource {

	@Inject
	private NginxFileSystemResourceImpl nginxFileSystemResourceImpl;

	@POST
	@Path("configure")
	public void configure(NginxConfigureRequest nginxConfigureRequest, @Suspended AsyncResponse asyncResponse) {
		nginxFileSystemResourceImpl.configure(nginxConfigureRequest.getHome(), nginxConfigureRequest.getMaxPostSize(),
				nginxConfigureRequest.getGzip());
		asyncResponse.resume(Response.ok(new NginxConfigureResponse(nginxConfigureRequest.getHome(),
				nginxConfigureRequest.getMaxPostSize(), nginxConfigureRequest.getGzip())).build());
	}
	
	@POST
	@Path("log/collect")
	public void logRotate(NginxLogCollectRequest nginxLogCollectRequest,
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxLogCollectResponse(
								nginxFileSystemResourceImpl.collect(nginxLogCollectRequest.getHome())))
						.build());
	}
	
	@POST
	@Path("log/rotate")
	public void logRotate(NginxLogRotateRequest nginxLogRotateRequest,
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxLogRotateResponse(
								nginxFileSystemResourceImpl.rotate(nginxLogRotateRequest.getHome())))
						.build());
	}

	@POST
	@Path("permission")
	public void permission(NginxFileSystemPermissionRequest nginxFileSystemPermissionRequest,
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxFileSystemPermissionResponse(
								nginxFileSystemResourceImpl.hasPermissionToWrite(nginxFileSystemPermissionRequest.getPath())))
						.build());
	}
}
