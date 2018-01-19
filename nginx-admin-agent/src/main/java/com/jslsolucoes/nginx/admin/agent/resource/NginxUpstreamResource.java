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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxUpstreamCreateRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxUpstreamDeleteRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxUpstreamCreateResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxUpstreamDeleteResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxUpstreamResourceImpl;

@Path("upstream")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxUpstreamResource {

	@Inject
	private NginxUpstreamResourceImpl nginxUpstreamResourceImpl;
	
	@POST
	@Path("create")
	public void create(NginxUpstreamCreateRequest nginxUpstreamCreateRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxUpstreamResourceImpl.create(nginxUpstreamCreateRequest.getHome(),
				nginxUpstreamCreateRequest.getName(), nginxUpstreamCreateRequest.getUuid(), nginxUpstreamCreateRequest.getStrategy(),
				nginxUpstreamCreateRequest.getEndpoints());
		asyncResponse.resume(Response
				.ok(new NginxUpstreamCreateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
	
	@POST
	@Path("delete")
	public void delete(NginxUpstreamDeleteRequest nginxUpstreamDeleteRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxUpstreamResourceImpl.delete(nginxUpstreamDeleteRequest.getHome(), nginxUpstreamDeleteRequest.getUuid());
		asyncResponse.resume(Response
				.ok(new NginxUpstreamDeleteResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
}
