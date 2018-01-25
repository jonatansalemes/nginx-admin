package com.jslsolucoes.nginx.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import com.google.common.collect.Lists;
import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.ResourceIdentifier;
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.model.VirtualHostAlias;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation;
import com.jslsolucoes.nginx.admin.repository.SslCertificateRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostRepository;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.tagria.lib.form.FormValidation;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("virtualHost")
public class VirtualHostController {

	private Result result;
	private VirtualHostRepository virtualHostRepository;
	private UpstreamRepository upstreamRepository;
	private SslCertificateRepository sslCertificateRepository;

	@Deprecated
	public VirtualHostController() {
		
	}

	@Inject
	public VirtualHostController(Result result, VirtualHostRepository virtualHostRepository,
			UpstreamRepository upstreamRepository, SslCertificateRepository sslCertificateRepository) {
		this.result = result;
		this.virtualHostRepository = virtualHostRepository;
		this.upstreamRepository = upstreamRepository;
		this.sslCertificateRepository = sslCertificateRepository;
	}

	@Path("list/{idNginx}")
	public void list(Long idNginx,boolean search, String term) {
		if (search) {
			this.result.include("virtualHostList", virtualHostRepository.searchFor(new Nginx(idNginx),term));
		} else {
			this.result.include("virtualHostList", virtualHostRepository.listAllFor(new Nginx(idNginx)));
		}
		this.result.include("nginx",new Nginx(idNginx));
	}

	@Path("form/{idNginx}")
	public void form(Long idNginx) {
		this.result.include("upstreamList", upstreamRepository.listAllFor(new Nginx(idNginx)));
		this.result.include("sslCertificateList", sslCertificateRepository.listAllFor(new Nginx(idNginx)));
		this.result.include("nginx",new Nginx(idNginx));
	}

	public void validate(Long id, Integer https, String idResourceIdentifier, Long idSslCertificate,
			List<String> aliases, List<String> locations, List<Long> upstreams,Long idNginx) {
		this.result.use(Results.json())
				.from(FormValidation.newBuilder().toUnordenedList(virtualHostRepository.validateBeforeSaveOrUpdate(
						new VirtualHost(id, https, new SslCertificate(idSslCertificate),
								new ResourceIdentifier(idResourceIdentifier),new Nginx(idNginx)),
						convert(aliases), convert(locations, upstreams))), "errors")
				.serialize();
	}

	@Path("edit/{idNginx}/{id}")
	public void edit(Long idNginx,Long id) {
		this.result.include("virtualHost", virtualHostRepository.load(new VirtualHost(id)));
		this.result.forwardTo(this).form(idNginx);
	}

	@Path("delete/{idNginx}/{id}")
	public void delete(Long idNginx,Long id) throws IOException {
		this.result.include("operation", virtualHostRepository.deleteWithResource(new VirtualHost(id)));
		this.result.redirectTo(this).list(idNginx,false, null);
	}

	@Post
	public void saveOrUpdate(Long id, Integer https, Long idResourceIdentifier, Long idSslCertificate,
			List<String> aliases, List<String> locations, List<Long> upstreams,Long idNginx) throws NginxAdminException {
		OperationResult operationResult = virtualHostRepository
				.saveOrUpdate(
						new VirtualHost(id, https, new SslCertificate(idSslCertificate),
								new ResourceIdentifier(idResourceIdentifier),new Nginx(idNginx)),
						convert(aliases), convert(locations, upstreams));
		this.result.include("operation", operationResult.getOperationType());
		this.result.redirectTo(this).edit(idNginx,operationResult.getId());
	}

	private List<VirtualHostLocation> convert(List<String> locations, List<Long> upstreams) {
		AtomicInteger atomicInteger = new AtomicInteger(0);
		return Lists.transform(locations, location -> new VirtualHostLocation(location,
				new Upstream(upstreams.get(atomicInteger.getAndIncrement()))));
	}

	private List<VirtualHostAlias> convert(List<String> aliases) {
		return Lists.transform(aliases, alias -> new VirtualHostAlias(alias));
	}
}
