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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxSslCreateRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxSslCreateResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxSslResourceImpl;

@Path("ssl")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxSslResource {

	private NginxSslResourceImpl nginxSslResourceImpl;
	
	@Deprecated
	public NginxSslResource() {
		
	}
	
	@Inject
	public NginxSslResource(NginxSslResourceImpl nginxSslResourceImpl) {
		this.nginxSslResourceImpl = nginxSslResourceImpl;
	}
	
	@POST
	public void create(NginxSslCreateRequest nginxSslCreateRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxSslResourceImpl.create(nginxSslCreateRequest.getCertificate(),nginxSslCreateRequest.getCertificateUuid(), nginxSslCreateRequest.getCertificatePrivateKey(),nginxSslCreateRequest.getCertificatePrivateKeyUuid());
		asyncResponse.resume(Response
				.ok(new NginxSslCreateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
	
	@DELETE
	@Path("{uuid}")
	public void delete(@PathParam("uuid") String uuid, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxSslResourceImpl.delete(uuid);
		asyncResponse.resume(Response
				.ok(new NginxSslCreateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}

}
