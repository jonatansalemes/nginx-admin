package com.jslsolucoes.nginx.admin.controller;

import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.repository.SslCertificateRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;

@Controller
@Path("certificate")
public class SslCertificateController {

	private Result result;
	private SslCertificateRepository sslCertificateRepository;

	public SslCertificateController(Result result, SslCertificateRepository sslCertificateRepository) {
		this.result = result;
		this.sslCertificateRepository = sslCertificateRepository;
	}

	public void list() {
		this.result.include("sslCertificateList", sslCertificateRepository.listAll());
	}

	public void form() {

	}

	@Path("edit/{id}")
	public void edit(Long id) {
		this.result.include("sslCertificate", sslCertificateRepository.load(new SslCertificate(id)));
		this.result.forwardTo(this).form();
	}

	@Path("delete/{id}")
	public void delete(Long id) {
		sslCertificateRepository.delete(new SslCertificate(id));
	}

	@Post
	public void upload(UploadedFile certificate, UploadedFile key) throws Exception {
		sslCertificateRepository.upload(certificate.getFile(), key.getFile());
	}
}
