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

import com.jslsolucoes.nginx.admin.agent.model.request.NginxStartRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxStartResponse;
import com.jslsolucoes.nginx.admin.agent.runner.exec.NginxCommandLineIteration;
import com.jslsolucoes.runtime.RuntimeResult;
import com.jslsolucoes.runtime.RuntimeResultType;


@Path("cli")
@Produces(MediaType.APPLICATION_JSON)
public class NginxCommandLineInterfaceResource {

	@Inject
	private NginxCommandLineIteration nginxCommandLineIteration;
	
	@POST
	@Path("start")
	public void start(NginxStartRequest nginxStartRequest,
			@Suspended AsyncResponse asyncResponse) {
		
		RuntimeResult runtimeResult = nginxCommandLineIteration.start(nginxStartRequest.getBin(), nginxStartRequest.getConf());
		if(runtimeResult.getRuntimeResultType().equals(RuntimeResultType.SUCCESS)){
			asyncResponse.resume(
					Response.ok(new NginxStartResponse(runtimeResult.getOutput())).build());
		} else {
			asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new NginxStartResponse(runtimeResult.getOutput()))
					.build());
		}
	}

}