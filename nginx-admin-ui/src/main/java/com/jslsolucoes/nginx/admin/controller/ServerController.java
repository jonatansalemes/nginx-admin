package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.repository.ServerRepository;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.tagria.lib.form.FormValidation;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("server")
public class ServerController {

	private Result result;
	private ServerRepository serverRepository;

	public ServerController() {
		
	}

	@Inject
	public ServerController(Result result, ServerRepository serverRepository) {
		this.result = result;
		this.serverRepository = serverRepository;
	}

	@Path("list/{idNginx}")
	public void list(Long idNginx) {
		this.result.include("serverList", serverRepository.listAllFor(new Nginx(idNginx)));
		this.result.include("nginx",new Nginx(idNginx));
	}

	@Path("form/{idNginx}")
	public void form(Long idNginx) {
		this.result.include("nginx",new Nginx(idNginx));
	}

	public void validate(Long id, String ip,Long idNginx) {
		this.result.use(Results.json())
				.from(FormValidation.newBuilder().toUnordenedList(serverRepository.validateBeforeSaveOrUpdate(new Server(id, ip,new Nginx(idNginx)))),
						"errors")
				.serialize();
	}

	@Path("edit/{idNginx}/{id}")
	public void edit(Long idNginx,Long id) {
		this.result.include("server",serverRepository.load(new Server(id)));
		this.result.forwardTo(this).form(idNginx);
	}

	@Path("delete/{idNginx}/{id}")
	public void delete(Long idNginx,Long id) {
		this.result.include("operation", serverRepository.delete(new Server(id)));
		this.result.redirectTo(this).list(idNginx);
	}

	@Post
	public void saveOrUpdate(Long id, String ip,Long idNginx) {
		OperationResult operationResult = serverRepository.saveOrUpdate(new Server(id, ip,new Nginx(idNginx)));
		this.result.include("operation", operationResult.getOperationType());
		this.result.redirectTo(this).edit(idNginx,operationResult.getId());
	}
}
