package com.jslsolucoes.nginx.admin.controller;

import java.io.IOException;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.ImportRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
@Path("import")
public class ImportController {

	private Result result;
	private ImportRepository importRepository;

	@Deprecated
	public ImportController() {

	}

	@Inject
	public ImportController(Result result, ImportRepository importRepository) {
		this.result = result;
		this.importRepository = importRepository;
	}

	@Path("form/{idNginx}")
	public void form(Long idNginx) {
		this.result.include("nginx", new Nginx(idNginx));
	}

	@Post
	public void execute(Long idNginx, String nginxConf) throws IOException, NginxAdminException {
		importRepository.importFrom(new Nginx(idNginx), nginxConf);
		this.result.include("import", true);
		this.result.redirectTo(this).form(idNginx);
	}

}
