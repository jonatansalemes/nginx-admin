package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.jslsolucoes.nginx.admin.agent.NginxAgentRunner;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxImportConfResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.model.VirtualHostAlias;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.DirectiveType;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.UpstreamDirective;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.VirtualHostDirective;
import com.jslsolucoes.nginx.admin.repository.ImportRepository;
import com.jslsolucoes.nginx.admin.repository.ServerRepository;
import com.jslsolucoes.nginx.admin.repository.SslCertificateRepository;
import com.jslsolucoes.nginx.admin.repository.StrategyRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostRepository;

@RequestScoped
public class ImportRepositoryImpl implements ImportRepository {

	private ServerRepository serverRepository;
	private UpstreamRepository upstreamRepository;
	private StrategyRepository strategyRepository;
	private VirtualHostRepository virtualHostRepository;
	private SslCertificateRepository sslCertificateRepository;
	private NginxAgentRunner nginxAgentRunner;

	@Deprecated
	public ImportRepositoryImpl() {
		
	}

	@Inject
	public ImportRepositoryImpl(ServerRepository serverRepository, UpstreamRepository upstreamRepository,
			StrategyRepository strategyRepository, VirtualHostRepository virtualHostRepository,
			SslCertificateRepository sslCertificateRepository,NginxAgentRunner nginxAgentRunner) {
		this.serverRepository = serverRepository;
		this.upstreamRepository = upstreamRepository;
		this.strategyRepository = strategyRepository;
		this.virtualHostRepository = virtualHostRepository;
		this.sslCertificateRepository = sslCertificateRepository;
		this.nginxAgentRunner = nginxAgentRunner;
	}

	@Override
	public void importFrom(Nginx nginx,String nginxConf) {
		NginxResponse nginxResponse = nginxAgentRunner.importFromNginxConfiguration(nginx.getId(), nginxConf);
		if(nginxResponse.success()){
			NginxImportConfResponse nginxImportConfResponse = (NginxImportConfResponse) nginxResponse;
			List<Directive> directives = nginxImportConfResponse.getDirectives();
			servers(directives);
			upstreams(directives);
			virtualHosts(directives);
		} else {
			NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
			System.out.println(nginxExceptionResponse.getStackTrace());
		}
	}

	private List<Directive> filter(List<Directive> directives, DirectiveType directiveType) {
		return directives.stream().filter(directive -> directive.type().equals(directiveType))
				.collect(Collectors.toList());
	}

	private void virtualHosts(List<Directive> directives) {
		for (Directive directive : filter(directives, DirectiveType.VIRTUAL_HOST)) {
			VirtualHostDirective virtualHostDirective = (VirtualHostDirective) directive;

			List<VirtualHostAlias> aliases = virtualHostDirective.getAliases().stream()
					.map(alias -> new VirtualHostAlias(alias)).collect(Collectors.toList());

			List<VirtualHostLocation> locations = virtualHostDirective.getLocations().stream()
					.filter(location -> !StringUtils.isEmpty(location.getUpstream()))
					.map(location -> new VirtualHostLocation(location.getPath(),
							upstreamRepository.findByName(location.getUpstream())))
					.collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(aliases) && !CollectionUtils.isEmpty(locations)
					&& virtualHostRepository.hasEquals(new VirtualHost(), aliases) == null) {
				SslCertificate sslCertificate = null;
				if (virtualHostDirective.getSslCertificate() != null) {
						OperationResult operationResult = sslCertificateRepository.saveOrUpdate(
								new SslCertificate(UUID.randomUUID().toString()));
						sslCertificate = new SslCertificate(operationResult.getId());
				}
				virtualHostRepository.saveOrUpdate(
						new VirtualHost(virtualHostDirective.getPort() == 80 ? 0 : 1, sslCertificate), aliases,
						locations);
			}
		}
	}

	private void upstreams(List<Directive> directives) {
		for (Directive directive : filter(directives, DirectiveType.UPSTREAM)) {
			UpstreamDirective upstreamDirective = (UpstreamDirective) directive;
			if (upstreamRepository.hasEquals(new Upstream(upstreamDirective.getName())) == null) {
				upstreamRepository.saveOrUpdate(
						new Upstream(upstreamDirective.getName(),
								strategyRepository.searchFor(upstreamDirective.getStrategy())),
						Lists.transform(upstreamDirective.getServers(), upstreamDirectiveServer -> new UpstreamServer(
								serverRepository.findByIp(upstreamDirectiveServer.getIp()),
								upstreamDirectiveServer.getPort() == null ? 80 : upstreamDirectiveServer.getPort())));
			}
		}
	}

	private void servers(List<Directive> directives) {
		directives.stream().filter(directive -> directive.type().equals(DirectiveType.UPSTREAM)).forEach(directive -> {
			UpstreamDirective upstreamDirective = (UpstreamDirective) directive;
			upstreamDirective.getServers().stream().forEach(server -> {
				if (serverRepository.hasEquals(new Server(server.getIp())) == null) {
					serverRepository.insert(new Server(server.getIp()));
				}
			});
		});
	}

}
