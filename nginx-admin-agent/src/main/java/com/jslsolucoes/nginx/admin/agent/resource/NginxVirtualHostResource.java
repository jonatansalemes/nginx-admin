package com.jslsolucoes.nginx.admin.agent.resource;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.auth.AuthHandler;
import com.jslsolucoes.nginx.admin.agent.error.ErrorHandler;
import com.jslsolucoes.nginx.admin.agent.model.request.virtual.host.NginxVirtualHostCreateRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.virtual.host.NginxVirtualHostCreateResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.virtual.host.NginxVirtualHostDeleteResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxVirtualHostResourceImpl;

@Path("virtualHost")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxVirtualHostResource {

	
	private NginxVirtualHostResourceImpl nginxVirtualHostResourceImpl;
	
	@Deprecated
	public NginxVirtualHostResource() {
		
	}
	
	@Inject
	public NginxVirtualHostResource(NginxVirtualHostResourceImpl nginxVirtualHostResourceImpl) {
		this.nginxVirtualHostResourceImpl = nginxVirtualHostResourceImpl;
	}
	
	@POST
	public void create(NginxVirtualHostCreateRequest nginxVirtualHostCreateRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxVirtualHostResourceImpl.create(nginxVirtualHostCreateRequest.getUuid(), nginxVirtualHostCreateRequest.getHttps(), nginxVirtualHostCreateRequest.getCertificateUuid(), nginxVirtualHostCreateRequest.getCertificatePrivateKeyUuid(), nginxVirtualHostCreateRequest.getAliases(), nginxVirtualHostCreateRequest.getLocations());
		asyncResponse.resume(Response
				.ok(new NginxVirtualHostCreateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
	
	@DELETE
	@Path("{uuid}")
	public void delete(@PathParam("uuid") String uuid,@Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxVirtualHostResourceImpl.delete(uuid);
		asyncResponse.resume(Response
				.ok(new NginxVirtualHostDeleteResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
}
