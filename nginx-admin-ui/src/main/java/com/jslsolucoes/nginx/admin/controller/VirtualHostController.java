package com.jslsolucoes.nginx.admin.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
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
import com.jslsolucoes.nginx.admin.util.HtmlUtil;

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

	public VirtualHostController() {

	}

	@Inject
	public VirtualHostController(Result result, VirtualHostRepository virtualHostRepository,
			UpstreamRepository upstreamRepository,SslCertificateRepository sslCertificateRepository) {
		this.result = result;
		this.virtualHostRepository = virtualHostRepository;
		this.upstreamRepository = upstreamRepository;
		this.sslCertificateRepository = sslCertificateRepository;
	}

	public void list(boolean search,String term) {
		if(search) {
			this.result.include("virtualHostList", virtualHostRepository.search(term));
		} else {
			this.result.include("virtualHostList", virtualHostRepository.listAll());
		}
	}

	public void form() {
		this.result.include("upstreamList",upstreamRepository.listAll());
		this.result.include("sslCertificateList",sslCertificateRepository.listAll());
	}

	public void validate(Long id, Integer https, Long idUpstream, String idResourceIdentifier,
			Long idSslCertificate,List<String> aliases,
			List<String> locations, List<Long> upstreams) {
		this.result
				.use(Results.json()).from(
						HtmlUtil.convertToUnodernedList(virtualHostRepository.validateBeforeSaveOrUpdate(
								new VirtualHost(id, https, new SslCertificate(idSslCertificate), new ResourceIdentifier(idResourceIdentifier)),convert(aliases),convert(locations,upstreams))),
						"errors")
				.serialize();
	}

	@Path("edit/{id}")
	public void edit(Long id) {
		this.result.include("virtualHost", virtualHostRepository.load(new VirtualHost(id)));
		this.result.forwardTo(this).form();
	}

	@Path("delete/{id}")
	public void delete(Long id) throws Exception {
		this.result.include("operation", virtualHostRepository.delete(new VirtualHost(id)));
		this.result.redirectTo(this).list(false,null);
	}

	@Post
	public void saveOrUpdate(Long id, Integer https, Long idUpstream, Long idResourceIdentifier,
			Long idSslCertificate,List<String> aliases,
			List<String> locations, List<Long> upstreams) throws Exception {
		OperationResult operationResult = virtualHostRepository
				.saveOrUpdate(new VirtualHost(id,  https, new SslCertificate(idSslCertificate),
						 new ResourceIdentifier(idResourceIdentifier)),convert(aliases),convert(locations,upstreams));
		this.result.include("operation", operationResult.getOperationType());
		this.result.redirectTo(this).edit(operationResult.getId());
	}
	
	private List<VirtualHostLocation> convert(List<String> locations, List<Long> upstreams) {
		AtomicInteger atomicInteger = new AtomicInteger(0);
		return Lists.transform(locations, new Function<String, VirtualHostLocation>() {
			@Override
			public VirtualHostLocation apply(String location) {
				return new VirtualHostLocation(location,new Upstream(upstreams.get(atomicInteger.getAndIncrement())));
			}
		});
	}
	
	private List<VirtualHostAlias> convert(List<String> aliases) {
		return Lists.transform(aliases, new Function<String, VirtualHostAlias>() {
			@Override
			public VirtualHostAlias apply(String alias) {
				return new VirtualHostAlias(alias);
			}
		});
	}
}
