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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxErrorLogCollectRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxErrorLogRotateRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxLogCollectResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxLogRotateResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxErrorLogResourceImpl;

@Path("errorLog")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxErrorLogResource {

	@Inject
	private NginxErrorLogResourceImpl nginxErrorLogResourceImpl;
	
	@POST
	@Path("collect")
	public void collect(NginxErrorLogCollectRequest nginxErrorLogCollectRequest,
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxLogCollectResponse(
								nginxErrorLogResourceImpl.collect(nginxErrorLogCollectRequest.getHome())))
						.build());
	}
	
	@POST
	@Path("rotate")
	public void rotate(NginxErrorLogRotateRequest nginxErrorLogRotateRequest,
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxLogRotateResponse(
								nginxErrorLogResourceImpl.rotate(nginxErrorLogRotateRequest.getHome())))
						.build());
	}
	
}
