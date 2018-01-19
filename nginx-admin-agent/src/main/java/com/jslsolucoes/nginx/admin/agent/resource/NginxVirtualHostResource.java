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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxVirtualHostCreateRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxVirtualHostDeleteRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxVirtualHostCreateResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxVirtualHostDeleteResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxVirtualHostResourceImpl;

@Path("virtualHost")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxVirtualHostResource {

	@Inject
	private NginxVirtualHostResourceImpl nginxVirtualHostResourceImpl;
	
	@POST
	@Path("create")
	public void create(NginxVirtualHostCreateRequest nginxVirtualHostCreateRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxVirtualHostResourceImpl.create(nginxVirtualHostCreateRequest.getHome(), nginxVirtualHostCreateRequest.getUuid(), nginxVirtualHostCreateRequest.getHttps(), nginxVirtualHostCreateRequest.getCertificateUuid(), nginxVirtualHostCreateRequest.getCertificatePrivateKeyUuid(), nginxVirtualHostCreateRequest.getAliases(), nginxVirtualHostCreateRequest.getLocations());
		asyncResponse.resume(Response
				.ok(new NginxVirtualHostCreateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
	
	@POST
	@Path("delete")
	public void delete(NginxVirtualHostDeleteRequest nginxVirtualHostDeleteRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxVirtualHostResourceImpl.delete(nginxVirtualHostDeleteRequest.getHome(), nginxVirtualHostDeleteRequest.getUuid());
		asyncResponse.resume(Response
				.ok(new NginxVirtualHostDeleteResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
}
