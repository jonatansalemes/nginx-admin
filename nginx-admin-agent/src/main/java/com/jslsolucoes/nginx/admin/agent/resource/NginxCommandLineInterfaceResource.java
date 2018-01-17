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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxCliRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxCliResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxCommandLineInterfaceResourceImpl;
import com.jslsolucoes.runtime.RuntimeResult;

@Path("cli")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxCommandLineInterfaceResource {

	@Inject
	private NginxCommandLineInterfaceResourceImpl nginxCommandLineInterfaceResourceImpl;

	@POST
	@Path("start")
	public void start(NginxCliRequest nginxCliRequest, @Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.start(nginxCliRequest.getBin(),
				nginxCliRequest.getConf());
		asyncResponse.resume(Response.ok(new NginxCliResponse(runtimeResult.getOutput(),
				runtimeResult.isSuccess())).build());
	}

	@POST
	@Path("killAll")
	public void killAll(NginxCliRequest nginxCliRequest, @Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.killAll();
		asyncResponse.resume(
				Response.ok(new NginxCliResponse(runtimeResult.getOutput(), runtimeResult.isSuccess())).build());
	}

	@POST
	@Path("stop")
	public void stop(NginxCliRequest nginxCliRequest, @Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.stop(nginxCliRequest.getBin(),
				nginxCliRequest.getConf());
		asyncResponse.resume(
				Response.ok(new NginxCliResponse(runtimeResult.getOutput(), runtimeResult.isSuccess())).build());
	}

	@POST
	@Path("status")
	public void status(NginxCliRequest nginxCliRequest, @Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.status();
		asyncResponse.resume(
				Response.ok(new NginxCliResponse(runtimeResult.getOutput(), runtimeResult.isSuccess())).build());
	}

}