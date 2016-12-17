package com.jslsolucoes.nginx.admin.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.model.VirtualDomain;
import com.jslsolucoes.nginx.admin.nginx.parser.NginxConfParser;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.DirectiveType;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.ServerDirective;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.UpstreamDirective;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.UpstreamDirectiveServer;
import com.jslsolucoes.nginx.admin.repository.ImportRepository;
import com.jslsolucoes.nginx.admin.repository.ServerRepository;
import com.jslsolucoes.nginx.admin.repository.SslCertificateRepository;
import com.jslsolucoes.nginx.admin.repository.StrategyRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualDomainRepository;

@RequestScoped
public class ImportRepositoryImpl implements ImportRepository {

	private ServerRepository serverRepository;
	private UpstreamRepository upstreamRepository;
	private StrategyRepository strategyRepository;
	private VirtualDomainRepository virtualDomainRepository;
	private SslCertificateRepository sslCertificateRepository;

	public ImportRepositoryImpl() {

	}

	@Inject
	public ImportRepositoryImpl(ServerRepository serverRepository, UpstreamRepository upstreamRepository,
			StrategyRepository strategyRepository, VirtualDomainRepository virtualDomainRepository,
			SslCertificateRepository sslCertificateRepository) {
		this.serverRepository = serverRepository;
		this.upstreamRepository = upstreamRepository;
		this.strategyRepository = strategyRepository;
		this.virtualDomainRepository = virtualDomainRepository;
		this.sslCertificateRepository = sslCertificateRepository;
	}

	@Override
	public void importFrom(String nginxConf) throws IOException {
		List<Directive> directives = new NginxConfParser(nginxConf).parse();
		servers(directives);
		upstreams(directives);
		virtualHosts(directives);
	}

	private void virtualHosts(List<Directive> directives) {
		directives.stream().filter(directive -> directive.type().equals(DirectiveType.SERVER)).forEach(directive -> {
			try {
				ServerDirective serverDirective = ((ServerDirective) directive);
				serverDirective.getAliases().stream().forEach(domain -> {
					try {
						
						if(!StringUtils.isEmpty(serverDirective.getUpstream()) 
								&& virtualDomainRepository.hasEquals(new VirtualDomain(domain)) == null){
							SslCertificate sslCertificate = null;
							if (!StringUtils.isEmpty(serverDirective.getSslCertificate())) {
								OperationResult operationResult = sslCertificateRepository.saveOrUpdate(
										new SslCertificate(UUID.randomUUID().toString()),
										new FileInputStream(new File(serverDirective.getSslCertificate())),
										new FileInputStream(new File(serverDirective.getSslCertificateKey())));
								sslCertificate = new SslCertificate(operationResult.getId());
							}
							virtualDomainRepository
								.saveOrUpdate(new VirtualDomain(domain, (serverDirective.getPort() == 80 ? 0 : 1),
										sslCertificate, upstreamRepository.findByName(serverDirective.getUpstream())));
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	private void upstreams(List<Directive> directives) {
		directives.stream().filter(directive -> directive.type().equals(DirectiveType.UPSTREAM)).forEach(directive -> {
			try {
				UpstreamDirective upstreamDirective = ((UpstreamDirective) directive);
				if(upstreamRepository.hasEquals(new Upstream(upstreamDirective.getName())) == null){
					upstreamRepository.saveOrUpdate(
							new Upstream(upstreamDirective.getName(),
									strategyRepository.findByName(upstreamDirective.getStrategy())),
							Lists.transform(upstreamDirective.getServers(),
									new Function<UpstreamDirectiveServer, UpstreamServer>() {
										@Override
										public UpstreamServer apply(UpstreamDirectiveServer upstreamDirectiveServer) {
											return new UpstreamServer(
													serverRepository.findByIp(upstreamDirectiveServer.getIp()),
													(upstreamDirectiveServer.getPort() == null ? 80 : upstreamDirectiveServer.getPort()));
										}
									}));
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	private void servers(List<Directive> directives) {
		directives.stream().filter(directive -> directive.type().equals(DirectiveType.UPSTREAM)).forEach(directive -> {
			UpstreamDirective upstreamDirective = ((UpstreamDirective) directive);

			upstreamDirective.getServers().stream().forEach(server -> {
				if (serverRepository.hasEquals(new Server(server.getIp())) == null) {
					serverRepository.insert(new Server(server.getIp()));
				}
			});
		});
	}

	@Override
	public List<String> validateBeforeImport(String nginxConf) {
		List<String> errors = new ArrayList<String>();

		if (!new File(nginxConf).exists()) {
			errors.add(Messages.getString("import.nginx.conf.invalid", nginxConf));
		}

		return errors;
	}
}
