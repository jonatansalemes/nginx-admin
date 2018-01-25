package com.jslsolucoes.nginx.admin.agent.resource;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.jslsolucoes.nginx.admin.agent.auth.AuthHandler;
import com.jslsolucoes.nginx.admin.agent.error.ErrorHandler;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxUpstreamCreateRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.NginxUpstreamUpdateRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxUpstreamCreateResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxUpstreamDeleteResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxUpstreamReadResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxUpstreamUpdateResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxUpstreamResourceImpl;

@Path("upstream")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxUpstreamResource {

	@Inject
	private NginxUpstreamResourceImpl nginxUpstreamResourceImpl;
	
	@POST
	public void create(NginxUpstreamCreateRequest nginxUpstreamCreateRequest, @Suspended AsyncResponse asyncResponse,
			@Context UriInfo uriInfo) {
		NginxOperationResult nginxOperationResult = nginxUpstreamResourceImpl.create(nginxUpstreamCreateRequest.getHome(),
				nginxUpstreamCreateRequest.getName(), nginxUpstreamCreateRequest.getUuid(), nginxUpstreamCreateRequest.getStrategy(),
				nginxUpstreamCreateRequest.getEndpoints());
		 UriBuilder builder = uriInfo.getAbsolutePathBuilder();
	     builder.path(nginxUpstreamCreateRequest.getUuid());
	     asyncResponse.resume(Response
				.created(builder.build())
				.entity(new NginxUpstreamCreateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
	
	@PUT
	@Path("{uuid}")
	public void update(@PathParam("uuid") String uuid,NginxUpstreamUpdateRequest nginxUpstreamUpdateRequest, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxUpstreamResourceImpl.update(uuid,nginxUpstreamUpdateRequest.getHome(),
				nginxUpstreamUpdateRequest.getName(), nginxUpstreamUpdateRequest.getStrategy(),
				nginxUpstreamUpdateRequest.getEndpoints());
		asyncResponse.resume(Response
				.ok(new NginxUpstreamUpdateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
	
	@DELETE
	@Path("{uuid}")
	public void delete(@PathParam("uuid") String uuid,@QueryParam("home") String home, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxUpstreamResourceImpl.delete(home, uuid);
		asyncResponse.resume(Response
				.ok(new NginxUpstreamDeleteResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}
	
	@GET
	@Path("{uuid}")
	public void read(@PathParam("uuid") String uuid,@QueryParam("home") String home, @Suspended AsyncResponse asyncResponse) {
		FileObject fileObject = nginxUpstreamResourceImpl.read(home, uuid);
		asyncResponse.resume(Response
				.ok(new NginxUpstreamReadResponse(fileObject))
				.build());
	}
}
