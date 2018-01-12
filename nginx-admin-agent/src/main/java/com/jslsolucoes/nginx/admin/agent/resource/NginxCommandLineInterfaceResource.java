package com.jslsolucoes.nginx.admin.agent.resource;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.jslsolucoes.nginx.admin.agent.model.request.NginxCliRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxCliResponse;
import com.jslsolucoes.nginx.admin.agent.runner.exec.NginxCommandLineInterface;
import com.jslsolucoes.runtime.RuntimeResult;
import com.jslsolucoes.runtime.RuntimeResultType;


@Path("cli")
@Produces(MediaType.APPLICATION_JSON)
public class NginxCommandLineInterfaceResource {

	@Inject
	private NginxCommandLineInterface nginxCommandLineInterface;
	
	@POST
	@Path("start")
	public void start(NginxCliRequest nginxCliRequest,
			@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterface.start(nginxCliRequest.getBin(), nginxCliRequest.getConf());
		if(runtimeResult.getRuntimeResultType().equals(RuntimeResultType.SUCCESS)){
			asyncResponse.resume(
					Response.ok(new NginxCliResponse(runtimeResult.getOutput())).build());
		} else {
			asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new NginxCliResponse(runtimeResult.getOutput()))
					.build());
		}
	}
	
	@POST
	@Path("kill")
	public void kill(NginxCliRequest nginxCliRequest,
			@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterface.kill();
		if(runtimeResult.getRuntimeResultType().equals(RuntimeResultType.SUCCESS)){
			asyncResponse.resume(
					Response.ok(new NginxCliResponse(runtimeResult.getOutput())).build());
		} else {
			asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new NginxCliResponse(runtimeResult.getOutput()))
					.build());
		}
	}
	
	@POST
	@Path("stop")
	public void stop(NginxCliRequest nginxCliRequest,
			@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterface.stop(nginxCliRequest.getBin(), nginxCliRequest.getConf());
		if(runtimeResult.getRuntimeResultType().equals(RuntimeResultType.SUCCESS)){
			asyncResponse.resume(
					Response.ok(new NginxCliResponse(runtimeResult.getOutput())).build());
		} else {
			asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new NginxCliResponse(runtimeResult.getOutput()))
					.build());
		}
	}
	
	@POST
	@Path("status")
	public void status(NginxCliRequest nginxCliRequest,
			@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterface.status();
		if(runtimeResult.getRuntimeResultType().equals(RuntimeResultType.SUCCESS)){
			asyncResponse.resume(
					Response.ok(new NginxCliResponse(runtimeResult.getOutput())).build());
		} else {
			asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new NginxCliResponse(runtimeResult.getOutput()))
					.build());
		}
	}

}