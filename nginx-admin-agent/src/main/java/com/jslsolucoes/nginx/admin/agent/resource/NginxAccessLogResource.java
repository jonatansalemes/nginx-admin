package com.jslsolucoes.nginx.admin.agent.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.auth.AuthHandler;
import com.jslsolucoes.nginx.admin.agent.error.ErrorHandler;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxLogCollectResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxLogRotateResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxAccessLogResourceImpl;

@Path("accessLog")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxAccessLogResource {

	private NginxAccessLogResourceImpl nginxAccessLogResourceImpl;
	
	@Deprecated
	public NginxAccessLogResource() {
		
	}

	@Inject
	public NginxAccessLogResource(NginxAccessLogResourceImpl nginxAccessLogResourceImpl) {
		this.nginxAccessLogResourceImpl = nginxAccessLogResourceImpl;
	}
	
	@GET
	@Path("collect")
	public void collect(
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxLogCollectResponse(
								nginxAccessLogResourceImpl.collect()))
						.build());
	}
	
	@GET
	@Path("rotate")
	public void rotate(
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxLogRotateResponse(
								nginxAccessLogResourceImpl.rotate()))
						.build());
	}

}
