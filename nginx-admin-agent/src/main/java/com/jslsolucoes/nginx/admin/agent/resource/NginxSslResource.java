package com.jslsolucoes.nginx.admin.agent.resource;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
import com.jslsolucoes.nginx.admin.agent.model.request.ssl.NginxSslCreateRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.ssl.NginxSslUpdateRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.ssl.NginxSslCreateResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.ssl.NginxSslDeleteResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.ssl.NginxSslReadResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.ssl.NginxSslUpdateResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxSslResourceImpl;

@Path("ssl")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxSslResource {

	private NginxSslResourceImpl nginxSslResourceImpl;

	@Deprecated
	public NginxSslResource() {

	}

	@Inject
	public NginxSslResource(NginxSslResourceImpl nginxSslResourceImpl) {
		this.nginxSslResourceImpl = nginxSslResourceImpl;
	}

	@POST
	public void create(NginxSslCreateRequest nginxSslCreateRequest, @Suspended AsyncResponse asyncResponse,
			@Context UriInfo uriInfo) {
		NginxOperationResult nginxOperationResult = nginxSslResourceImpl.create(nginxSslCreateRequest.getUuid(),
				nginxSslCreateRequest.getFileObject());

		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		uriBuilder.path(nginxSslCreateRequest.getUuid());
		asyncResponse.resume(Response.created(uriBuilder.build())
				.entity(new NginxSslCreateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}

	@PUT
	@Path("{uuid}")
	public void update(@PathParam("uuid") String uuid, NginxSslUpdateRequest nginxSslUpdateRequest,
			@Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxSslResourceImpl.update(uuid,
				nginxSslUpdateRequest.getFileObject());
		asyncResponse.resume(Response
				.ok(new NginxSslUpdateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}

	@DELETE
	@Path("{uuid}")
	public void delete(@PathParam("uuid") String uuid, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxSslResourceImpl.delete(uuid);
		asyncResponse.resume(Response
				.ok(new NginxSslDeleteResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}

	@GET
	@Path("{uuid}")
	public void read(@PathParam("uuid") String uuid, @Suspended AsyncResponse asyncResponse) {
		FileObject fileObject = nginxSslResourceImpl.read(uuid);
		asyncResponse.resume(Response.ok(new NginxSslReadResponse(fileObject)).build());
	}

}
