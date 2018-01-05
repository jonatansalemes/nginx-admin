package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.repository.ImportRepository;
import com.jslsolucoes.tagria.lib.form.FormValidation;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("import")
public class ImportController {

	private Result result;
	private ImportRepository importRepository;

	public ImportController() {
		this(null, null);
	}

	@Inject
	public ImportController(Result result, ImportRepository importRepository) {
		this.result = result;
		this.importRepository = importRepository;
	}

	public void validate(String nginxConf) {
		this.result.use(Results.json())
				.from(FormValidation.newBuilder().toUnordenedList(importRepository.validateBeforeImport(nginxConf)), "errors")
				.serialize();
	}

	public void form() {
		// form logic
	}

	@Post
	public void execute(String nginxConf) throws NginxAdminException {
		importRepository.importFrom(nginxConf);
		this.result.include("imported", true);
		this.result.redirectTo(this).form();
	}

}
