package com.jslsolucoes.nginx.admin.agent.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jslsolucoes.nginx.admin.agent.auth.AuthHandler;
import com.jslsolucoes.nginx.admin.agent.error.ErrorHandler;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxCommandLineInterfaceResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxCommandLineInterfaceResourceImpl;
import com.jslsolucoes.runtime.RuntimeResult;

@Path("commandLineInterface")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxCommandLineInterfaceResource {

	private NginxCommandLineInterfaceResourceImpl nginxCommandLineInterfaceResourceImpl;

	@Deprecated
	public NginxCommandLineInterfaceResource() {

	}

	@Inject
	public NginxCommandLineInterfaceResource(
			NginxCommandLineInterfaceResourceImpl nginxCommandLineInterfaceResourceImpl) {
		this.nginxCommandLineInterfaceResourceImpl = nginxCommandLineInterfaceResourceImpl;
	}

	@GET
	@Path("start")
	public void start(@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.start();
		asyncResponse.resume(
				Response.ok(new NginxCommandLineInterfaceResponse(runtimeResult.getOutput(), runtimeResult.isSuccess()))
						.build());
	}

	@GET
	@Path("killAll")
	public void killAll(@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.killAll();
		asyncResponse.resume(
				Response.ok(new NginxCommandLineInterfaceResponse(runtimeResult.getOutput(), runtimeResult.isSuccess()))
						.build());
	}

	@GET
	@Path("stop")
	public void stop(@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.stop();
		asyncResponse.resume(
				Response.ok(new NginxCommandLineInterfaceResponse(runtimeResult.getOutput(), runtimeResult.isSuccess()))
						.build());
	}

	@GET
	@Path("status")
	public void status(@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.status();
		asyncResponse.resume(
				Response.ok(new NginxCommandLineInterfaceResponse(runtimeResult.getOutput(), runtimeResult.isSuccess()))
						.build());
	}

	@GET
	@Path("testConfiguration")
	public void testConfiguration(@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.testConfiguration();
		asyncResponse.resume(
				Response.ok(new NginxCommandLineInterfaceResponse(runtimeResult.getOutput(), runtimeResult.isSuccess()))
						.build());
	}

	@GET
	@Path("version")
	public void version(@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.version();
		asyncResponse.resume(
				Response.ok(new NginxCommandLineInterfaceResponse(runtimeResult.getOutput(), runtimeResult.isSuccess()))
						.build());
	}

	@GET
	@Path("reload")
	public void reload(@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.reload();
		asyncResponse.resume(
				Response.ok(new NginxCommandLineInterfaceResponse(runtimeResult.getOutput(), runtimeResult.isSuccess()))
						.build());
	}

	@GET
	@Path("restart")
	public void restart(@Suspended AsyncResponse asyncResponse) {
		RuntimeResult runtimeResult = nginxCommandLineInterfaceResourceImpl.restart();
		asyncResponse.resume(
				Response.ok(new NginxCommandLineInterfaceResponse(runtimeResult.getOutput(), runtimeResult.isSuccess()))
						.build());
	}

}