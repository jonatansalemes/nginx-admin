package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.ResourceIdentifier;
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.VirtualDomain;
import com.jslsolucoes.nginx.admin.repository.SslCertificateRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualDomainRepository;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.util.HtmlUtil;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("virtualDomain")
public class VirtualDomainController {

	private Result result;
	private VirtualDomainRepository virtualDomainRepository;
	private UpstreamRepository upstreamRepository;
	private SslCertificateRepository sslCertificateRepository;

	public VirtualDomainController() {

	}

	@Inject
	public VirtualDomainController(Result result, VirtualDomainRepository virtualDomainRepository,
			UpstreamRepository upstreamRepository,SslCertificateRepository sslCertificateRepository) {
		this.result = result;
		this.virtualDomainRepository = virtualDomainRepository;
		this.upstreamRepository = upstreamRepository;
		this.sslCertificateRepository = sslCertificateRepository;
	}

	public void list() {
		this.result.include("virtualDomainList", virtualDomainRepository.listAll());
	}

	public void form() {
		this.result.include("upstreamList",upstreamRepository.listAll());
		this.result.include("sslCertificateList",sslCertificateRepository.listAll());
	}

	public void validate(Long id, String domain, Integer https, Long idUpstream, String idResourceIdentifier,
			Long idSslCertificate) {
		this.result
				.use(Results.json()).from(
						HtmlUtil.convertToUnodernedList(virtualDomainRepository.validateBeforeSaveOrUpdate(
								new VirtualDomain(id, domain, https, new SslCertificate(idSslCertificate),
										new Upstream(idUpstream), new ResourceIdentifier(idResourceIdentifier)))),
						"errors")
				.serialize();
	}

	@Path("edit/{id}")
	public void edit(Long id) {
		this.result.include("virtualDomain", virtualDomainRepository.load(new VirtualDomain(id)));
		this.result.forwardTo(this).form();
	}

	@Path("delete/{id}")
	public void delete(Long id) {
		this.result.include("operation", virtualDomainRepository.delete(new VirtualDomain(id)));
		this.result.redirectTo(this).list();
	}

	@Post
	public void saveOrUpdate(Long id, String domain, Integer https, Long idUpstream, Long idResourceIdentifier,
			Long idSslCertificate) throws Exception {
		OperationResult operationResult = virtualDomainRepository
				.saveOrUpdate(new VirtualDomain(id, domain, https, new SslCertificate(idSslCertificate),
						new Upstream(idUpstream), new ResourceIdentifier(idResourceIdentifier)));
		this.result.include("operation", operationResult.getOperationType());
		this.result.redirectTo(this).edit(operationResult.getId());
	}
}
