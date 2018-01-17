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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxUpstreamRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxVirtualHostRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxConfigureResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxUpstreamResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxVirtualHostResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxFileSystemResourceImpl;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;

@Path("fs")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxFileSystemResource {

	@Inject
	private NginxFileSystemResourceImpl nginxFileSystemResourceImpl;

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

	@POST
	@Path("upstream")
	public void upstream(NginxUpstreamRequest nginxUpstreamRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxFileSystemResourceImpl.upstream(nginxUpstreamRequest.getHome(),
				nginxUpstreamRequest.getName(), nginxUpstreamRequest.getUuid(), nginxUpstreamRequest.getStrategy(),
				nginxUpstreamRequest.getEndpoints());
		asyncResponse.resume(Response
				.ok(new NginxUpstreamResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
	
	@POST
	@Path("virtualHost")
	public void virtualHost(NginxVirtualHostRequest nginxVirtualHostRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxFileSystemResourceImpl.virtualHost(nginxVirtualHostRequest.getHome(), nginxVirtualHostRequest.getUuid(), nginxVirtualHostRequest.getHttps(), nginxVirtualHostRequest.getCertificate(), nginxVirtualHostRequest.getCertificatePrivateKey(), nginxVirtualHostRequest.getAliases(), nginxVirtualHostRequest.getLocations());
		asyncResponse.resume(Response
				.ok(new NginxVirtualHostResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}

}
