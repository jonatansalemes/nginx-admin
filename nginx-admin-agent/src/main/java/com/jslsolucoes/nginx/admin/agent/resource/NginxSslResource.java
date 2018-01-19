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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxSslCreateRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxSslDeleteRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxSslCreateResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxSslResourceImpl;

@Path("ssl")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxSslResource {

	@Inject
	private NginxSslResourceImpl nginxSslResourceImpl;
	
	@POST
	@Path("create")
	public void create(NginxSslCreateRequest nginxSslCreateRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxSslResourceImpl.create(nginxSslCreateRequest.getHome(), nginxSslCreateRequest.getCertificate(),nginxSslCreateRequest.getCertificateUuid(), nginxSslCreateRequest.getCertificatePrivateKey(),nginxSslCreateRequest.getCertificatePrivateKeyUuid());
		asyncResponse.resume(Response
				.ok(new NginxSslCreateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
	
	@POST
	@Path("delete")
	public void delete(NginxSslDeleteRequest nginxSslDeleteRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxSslResourceImpl.delete(nginxSslDeleteRequest.getHome(), nginxSslDeleteRequest.getCertificateUuid(), nginxSslDeleteRequest.getCertificatePrivateKeyUuid());
		asyncResponse.resume(Response
				.ok(new NginxSslCreateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}

}
