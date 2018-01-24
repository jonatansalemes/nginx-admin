package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.tagria.lib.form.FormValidation;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("nginx")
public class NginxController {

	private Result result;
	private NginxRepository nginxRepository;
	private NginxAgentClient nginxAgentClient;
	
	public NginxController() {
		
	}

	@Inject
	public NginxController(Result result, NginxRepository nginxRepository,NginxAgentClient nginxAgentClient) {
		this.result = result;
		this.nginxRepository = nginxRepository;
		this.nginxAgentClient = nginxAgentClient;
	}
	
	public void list() {
		this.result.include("nginxList", nginxRepository.listAll());
	}

	public void form() {
		
	}
	
	public void ping(String endpoint,String authorizationKey) { 
		NginxResponse nginxResponse = nginxAgentClient
			.api(NginxAgentClientApis.ping())
				.withEndpoint(endpoint)
				.withAuthorizationKey(authorizationKey)
				.build()
				.ping().join();
		this.result.include("nginxResponse",nginxResponse);
	}
	
	public void validate(Long id,String name, String endpoint,  String authorizationKey) {
		this.result.use(Results.json())
				.from(FormValidation.newBuilder().toUnordenedList(
						nginxRepository.validateBeforeSaveOrUpdate(new Nginx(id,name, endpoint,authorizationKey))),
						"errors")
				.serialize();
	}

	@Path("edit/{id}")
	public void edit(Long id) {
		this.result.include("nginx", nginxRepository.load(new Nginx(id)));
		this.result.forwardTo(this).form();
	}
	
	@Path("delete/{id}")
	public void delete(Long id) {
		this.result.include("operation", nginxRepository.delete(new Nginx(id)));
		this.result.redirectTo(this).list();
	}

	@Post
	public void saveOrUpdate(Long id,String name, String endpoint,  String authorizationKey)
			throws NginxAdminException {
		OperationResult operationResult = nginxRepository.saveOrUpdate(new Nginx(id,name, endpoint,authorizationKey));
		this.result.include("operation", operationResult.getOperationType());
		this.result.redirectTo(this).edit(operationResult.getId());
	}

}
