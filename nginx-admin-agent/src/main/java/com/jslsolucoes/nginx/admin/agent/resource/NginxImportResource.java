package com.jslsolucoes.nginx.admin.agent.resource;

import java.util.List;

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
import com.jslsolucoes.nginx.admin.agent.model.request.NginxImportConfRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxImportConfResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxImportResourceImpl;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;

@Path("import")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxImportResource {

	private NginxImportResourceImpl nginxImportResourceImpl;

	@Deprecated
	public NginxImportResource() {

	}

	@Inject
	public NginxImportResource(NginxImportResourceImpl nginxImportResourceImpl) {
		this.nginxImportResourceImpl = nginxImportResourceImpl;
	}

	@POST
	@Path("conf")
	public void conf(NginxImportConfRequest nginxImportConfRequest, @Suspended AsyncResponse asyncResponse) {
		List<Directive> directives = nginxImportResourceImpl.importFromConfiguration(nginxImportConfRequest.getConf());
		asyncResponse.resume(Response.ok(new NginxImportConfResponse(directives)).build());
	}
}
