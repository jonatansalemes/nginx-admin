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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxConfigureRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxServerInfoRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxConfigureResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxOperationalSystemInfoResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxServerInfoResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxStatusResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxAdminResourceImpl;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;
import com.jslsolucoes.nginx.admin.agent.resource.impl.nginx.NginxInfo;
import com.jslsolucoes.nginx.admin.agent.resource.impl.os.OperationalSystemInfo;
import com.jslsolucoes.nginx.admin.agent.resource.impl.status.NginxStatus;

@Path("admin")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxAdminResource {

	@Inject
	private NginxAdminResourceImpl nginxAdminResourceImpl;

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
		OperationalSystemInfo operationalSystemInfo = nginxAdminResourceImpl.operationSystemInfo();
		asyncResponse.resume(Response.ok(new NginxOperationalSystemInfoResponse(operationalSystemInfo.getArch(),
				operationalSystemInfo.getDistribution(), operationalSystemInfo.getName(),
				operationalSystemInfo.getVersion())).build());
	}

	@POST
	@Path("nginxInfo")
	public void nginxInfo(NginxServerInfoRequest nginxServerInfoRequest,
			@Suspended AsyncResponse asyncResponse) {
		NginxInfo nginxInfo = nginxAdminResourceImpl.nginxInfo(nginxServerInfoRequest.getBin(),
				nginxServerInfoRequest.getHome());
		asyncResponse.resume(Response.ok(new NginxServerInfoResponse(nginxInfo.getVersion(),
				nginxInfo.getAddress(), nginxInfo.getPid(), nginxInfo.getUptime())).build());
	}

	@GET
	@Path("status")
	public void status(@Suspended AsyncResponse asyncResponse) {
		NginxStatus nginxStatus = nginxAdminResourceImpl.status();
		asyncResponse.resume(Response.ok(new NginxStatusResponse(nginxStatus.getActiveConnection(),
				nginxStatus.getAccepts(), nginxStatus.getHandled(), nginxStatus.getRequests(), nginxStatus.getReading(),
				nginxStatus.getWriting(), nginxStatus.getWaiting())).build());
	}
}
