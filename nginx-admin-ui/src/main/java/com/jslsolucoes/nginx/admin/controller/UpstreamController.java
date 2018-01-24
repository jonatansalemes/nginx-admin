package com.jslsolucoes.nginx.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import com.google.common.collect.Lists;
import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.ResourceIdentifier;
import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.model.Strategy;
import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.repository.ServerRepository;
import com.jslsolucoes.nginx.admin.repository.StrategyRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamRepository;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.tagria.lib.form.FormValidation;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("upstream")
public class UpstreamController {

	private Result result;
	private UpstreamRepository upstreamRepository;
	private ServerRepository serverRepository;
	private StrategyRepository strategyRepository;

	public UpstreamController() {
		this(null, null, null, null);
	}

	@Inject
	public UpstreamController(Result result, UpstreamRepository upstreamRepository, ServerRepository serverRepository,
			StrategyRepository strategyRepository) {
		this.result = result;
		this.upstreamRepository = upstreamRepository;
		this.serverRepository = serverRepository;
		this.strategyRepository = strategyRepository;
	}

	public void list() {
		this.result.include("upstreamList", upstreamRepository.listAll());
	}

	public void form() {
		this.result.include("serverList", serverRepository.listAllFor(null));
		this.result.include("strategyList", strategyRepository.listAll());
	}

	public void validate(Long id, String name, Long idStrategy, List<Long> servers, List<Integer> ports,
			Long idResourceIdentifier) {
		this.result.use(Results.json())
				.from(FormValidation.newBuilder().toUnordenedList(upstreamRepository.validateBeforeSaveOrUpdate(
						new Upstream(id, name, new Strategy(idStrategy), new ResourceIdentifier(idResourceIdentifier)),
						convert(servers, ports))), "errors")
				.serialize();
	}

	@Path("edit/{id}")
	public void edit(Long id) {
		this.result.include("upstream", upstreamRepository.load(new Upstream(id)));
		this.result.forwardTo(this).form();
	}

	@Path("delete/{id}")
	public void delete(Long id) throws IOException {
		this.result.include("operation", upstreamRepository.deleteWithResource(new Upstream(id)));
		this.result.redirectTo(this).list();
	}

	@Post
	public void saveOrUpdate(Long id, String name, Long idStrategy, List<Long> servers, List<Integer> ports,
			Long idResourceIdentifier) throws NginxAdminException {
		OperationResult operationResult = upstreamRepository.saveOrUpdate(
				new Upstream(id, name, new Strategy(idStrategy), new ResourceIdentifier(idResourceIdentifier)),
				convert(servers, ports));
		this.result.include("operation", operationResult.getOperationType());
		this.result.redirectTo(this).edit(operationResult.getId());
	}

	private List<UpstreamServer> convert(List<Long> servers, List<Integer> ports) {
		AtomicInteger atomicInteger = new AtomicInteger(0);
		return Lists.transform(servers,
				server -> new UpstreamServer(new Server(server), ports.get(atomicInteger.getAndIncrement())));
	}
}
