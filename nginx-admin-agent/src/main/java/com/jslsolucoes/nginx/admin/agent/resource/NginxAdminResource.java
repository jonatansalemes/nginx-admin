package com.jslsolucoes.nginx.admin.agent.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.auth.AuthHandler;
import com.jslsolucoes.nginx.admin.agent.error.ErrorHandler;
import com.jslsolucoes.nginx.admin.agent.info.nginx.NginxInfo;
import com.jslsolucoes.nginx.admin.agent.info.nginx.NginxInfoDiscover;
import com.jslsolucoes.nginx.admin.agent.info.os.OperationalSystem;
import com.jslsolucoes.nginx.admin.agent.info.os.OperationalSystemInfo;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxConfigureRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxServerDescriptionRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxConfigureResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxOperationalSystemDescriptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxServerDescriptionResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxAdminResourceImpl;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;

@Path("admin")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxAdminResource {

	@Inject
	private NginxAdminResourceImpl nginxAdminResourceImpl;
	
	@Inject
	private NginxInfoDiscover nginxInfoDiscover;

	@POST
	@Path("configure")
	public void configure(NginxConfigureRequest nginxConfigureRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxAdminResourceImpl.configure(nginxConfigureRequest.getHome(),
				nginxConfigureRequest.getMaxPostSize(), nginxConfigureRequest.getGzip());
		asyncResponse.resume(Response
				.ok(new NginxConfigureResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}

	@GET
	@Path("operationalSystemInfo")
	public void operationSystemInfo(@Suspended AsyncResponse asyncResponse) {
		OperationalSystemInfo operationalSystemInfo = OperationalSystem.info();
		asyncResponse.resume(Response.ok(new NginxOperationalSystemDescriptionResponse(operationalSystemInfo.getArch(),
				operationalSystemInfo.getDistribution(), operationalSystemInfo.getName(),
				operationalSystemInfo.getVersion())).build());
	}

	@POST
	@Path("nginxInfo")
	public void nginxInfo(NginxServerDescriptionRequest nginxServerDescriptionRequest,@Suspended AsyncResponse asyncResponse) {
		NginxInfo nginxInfo = nginxInfoDiscover.details(nginxServerDescriptionRequest.getBin(),nginxServerDescriptionRequest.getHome());
		asyncResponse.resume(Response
				.ok(new NginxServerDescriptionResponse(nginxInfo.getVersion(), nginxInfo.getAddress(), nginxInfo.getPid(), nginxInfo.getUptime()))
				.build());
	}
}
