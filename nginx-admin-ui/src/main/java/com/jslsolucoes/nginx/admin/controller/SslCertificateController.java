package com.jslsolucoes.nginx.admin.controller;

import java.io.IOException;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.ResourceIdentifier;
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.repository.SslCertificateRepository;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.tagria.lib.form.FormValidation;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("sslCertificate")
public class SslCertificateController {

	private Result result;
	private SslCertificateRepository sslCertificateRepository;

	@Deprecated
	public SslCertificateController() {
		
	}

	@Inject
	public SslCertificateController(Result result, SslCertificateRepository sslCertificateRepository) {
		this.result = result;
		this.sslCertificateRepository = sslCertificateRepository;
	}

	@Path("list/{idNginx}")
	public void list(Long idNginx) {
		this.result.include("sslCertificateList", sslCertificateRepository.listAllFor(new Nginx(idNginx)));
		this.result.include("nginx", new Nginx(idNginx));
	}

	public void validate(Long id, String commonName, Long idResourceIdentifierCertificate,
			Long idResourceIdentifierCertificatePrivateKey, UploadedFile certificateFile,
			UploadedFile certificatePrivateKeyFile, Long idNginx) {
		this.result.use(Results.json()).from(FormValidation.newBuilder()
				.toUnordenedList(sslCertificateRepository.validateBeforeSaveOrUpdate(new SslCertificate(id, commonName,
						new ResourceIdentifier(idResourceIdentifierCertificate),
						new ResourceIdentifier(idResourceIdentifierCertificatePrivateKey), new Nginx(idNginx)))),
				"errors").serialize();
	}

	@Path("form/{idNginx}")
	public void form(Long idNginx) {
		this.result.include("nginx", new Nginx(idNginx));
	}


	@Path("edit/{idNginx}/{id}")
	public void edit(Long idNginx, Long id) {
		this.result.include("sslCertificate", sslCertificateRepository.load(new SslCertificate(id)));
		this.result.forwardTo(this).form(idNginx);
	}

	@Path("delete/{idNginx}/{id}")
	public void delete(Long idNginx, Long id) {
		this.result.include("operation", sslCertificateRepository.delete(new SslCertificate(id)));
		this.result.redirectTo(this).list(idNginx);
	}

	@Post
	public void saveOrUpdate(Long id, String commonName, Long idResourceIdentifierCertificate,
			Long idResourceIdentifierCertificatePrivateKey, UploadedFile certificateFile,
			UploadedFile certificatePrivateKeyFile, Long idNginx) throws IOException {
		OperationResult operationResult = sslCertificateRepository.saveOrUpdate(
				new SslCertificate(id, commonName, new ResourceIdentifier(idResourceIdentifierCertificate),
						new ResourceIdentifier(idResourceIdentifierCertificatePrivateKey), new Nginx(idNginx)),
				certificateFile != null ? certificateFile.getFile() : null,
				certificatePrivateKeyFile != null ? certificatePrivateKeyFile.getFile() : null);
		this.result.include("operation", operationResult.getOperationType());
		this.result.redirectTo(this).edit(idNginx, operationResult.getId());
	}
}
