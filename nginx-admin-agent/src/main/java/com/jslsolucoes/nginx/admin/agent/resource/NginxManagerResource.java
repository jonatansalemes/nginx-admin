package com.jslsolucoes.nginx.admin.agent.resource;

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
import com.jslsolucoes.runtime.RuntimeResult;
import com.jslsolucoes.runtime.RuntimeResultType;


@Path("manager")
@Produces(MediaType.APPLICATION_JSON)
public class NginxManagerResource {

	
	@POST
	@Path("start")
	public void conversation(NginxStartRequest nginxStartRequest,
			@Suspended AsyncResponse asyncResponse) {
		
		RuntimeResult runtimeResult = new RuntimeResult(RuntimeResultType.ERROR, "error");
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