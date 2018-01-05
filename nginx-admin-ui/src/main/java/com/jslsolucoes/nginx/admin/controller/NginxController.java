package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.nginx.status.NginxStatus;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
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
	private NginxStatus nginxStatus;

	public NginxController() {
		this(null, null, null);
	}

	@Inject
	public NginxController(Result result, NginxRepository nginxRepository, NginxStatus nginxStatus) {
		this.result = result;
		this.nginxRepository = nginxRepository;
		this.nginxStatus = nginxStatus;
	}

	public void validate(Long id, String bin, String settings, Integer gzip, Integer maxPostSize) {
		this.result.use(Results.json())
				.from(FormValidation.newBuilder().toUnordenedList(
						nginxRepository.validateBeforeSaveOrUpdate(new Nginx(id, bin, settings, gzip, maxPostSize))),
						"errors")
				.serialize();
	}

	public void edit() {
		this.result.include("nginx", this.nginxRepository.configuration());
	}

	@Post
	public void update(Long id, String bin, String settings, Integer gzip, Integer maxPostSize)
			throws NginxAdminException {
		this.nginxRepository.saveOrUpdateAndConfigure(new Nginx(id, bin, settings, gzip, maxPostSize));
		this.result.include("updated", true);
		this.result.redirectTo(this).edit();
	}

	public void status() {
		this.result.use(Results.json()).from(nginxStatus).serialize();
	}

}
