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
import com.jslsolucoes.nginx.admin.agent.model.response.NginxConfigureResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxAdminResourceImpl;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;

@Path("admin")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxAdminResource {

	@Inject
	private NginxAdminResourceImpl nginxFileSystemResourceImpl;

	@POST
	@Path("configure")
	public void configure(NginxConfigureRequest nginxConfigureRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxFileSystemResourceImpl.configure(
				nginxConfigureRequest.getHome(), nginxConfigureRequest.getMaxPostSize(),
				nginxConfigureRequest.getGzip());
		asyncResponse.resume(Response
				.ok(new NginxConfigureResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
}
