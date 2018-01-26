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
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxErrorLogResourceImpl;

@Path("errorLog")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxErrorLogResource {

	private NginxErrorLogResourceImpl nginxErrorLogResourceImpl;
	
	@Deprecated
	public NginxErrorLogResource() {
		
	}

	@Inject
	public NginxErrorLogResource(NginxErrorLogResourceImpl nginxErrorLogResourceImpl) {
		this.nginxErrorLogResourceImpl = nginxErrorLogResourceImpl;
	}
	
	@GET
	@Path("collect")
	public void collect(
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxLogCollectResponse(
								nginxErrorLogResourceImpl.collect()))
						.build());
	}
	
	@GET
	@Path("rotate")
	public void rotate(
			@Suspended AsyncResponse asyncResponse) {
		asyncResponse
				.resume(Response
						.ok(new NginxLogRotateResponse(
								nginxErrorLogResourceImpl.rotate()))
						.build());
	}
	
}
