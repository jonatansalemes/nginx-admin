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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxAccessLogCollectRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxAccessLogRotateRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxLogCollectResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxLogRotateResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxAccessLogResourceImpl;

@Path("accessLog")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxAccessLogResource {

	@Inject
	private NginxAccessLogResourceImpl nginxAccessLogResourceImpl;
	
	@POST
	@Path("collect")
	public void collect(NginxAccessLogCollectRequest nginxAccessLogCollectRequest,
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxLogCollectResponse(
								nginxAccessLogResourceImpl.collect(nginxAccessLogCollectRequest.getHome())))
						.build());
	}
	
	@POST
	@Path("rotate")
	public void rotate(NginxAccessLogRotateRequest nginxAccessLogRotateRequest,
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxLogRotateResponse(
								nginxAccessLogResourceImpl.rotate(nginxAccessLogRotateRequest.getHome())))
						.build());
	}

}
