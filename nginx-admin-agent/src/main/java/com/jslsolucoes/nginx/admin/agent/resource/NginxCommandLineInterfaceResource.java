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
import com.jslsolucoes.nginx.admin.agent.runner.exec.NginxCommandLineInterface;
import com.jslsolucoes.runtime.RuntimeResult;
import com.jslsolucoes.runtime.RuntimeResultType;

@Path("cli")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxCommandLineInterfaceResource {

	@Inject
	private NginxCommandLineInterface nginxCommandLineInterface;

	@POST
	@Path("start")
	public void start(NginxCliRequest nginxCliRequest, @Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterface.start(nginxCliRequest.getBin(),
				nginxCliRequest.getConf());
		asyncResponse.resume(Response.ok(new NginxCliResponse(runtimeResult.getOutput(),
				runtimeResult.getRuntimeResultType().equals(RuntimeResultType.SUCCESS))).build());
	}

	@POST
	@Path("kill")
	public void kill(NginxCliRequest nginxCliRequest, @Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterface.kill();
		asyncResponse.resume(
				Response.ok(new NginxCliResponse(runtimeResult.getOutput(), runtimeResult.isSuccess())).build());
	}

	@POST
	@Path("stop")
	public void stop(NginxCliRequest nginxCliRequest, @Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterface.stop(nginxCliRequest.getBin(),
				nginxCliRequest.getConf());
		asyncResponse.resume(
				Response.ok(new NginxCliResponse(runtimeResult.getOutput(), runtimeResult.isSuccess())).build());
	}

	@POST
	@Path("status")
	public void status(NginxCliRequest nginxCliRequest, @Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterface.status();
		asyncResponse.resume(
				Response.ok(new NginxCliResponse(runtimeResult.getOutput(), runtimeResult.isSuccess())).build());
	}

}