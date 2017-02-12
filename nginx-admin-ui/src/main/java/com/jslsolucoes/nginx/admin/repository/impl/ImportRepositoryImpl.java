/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.model.VirtualHostAlias;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation;
import com.jslsolucoes.nginx.admin.nginx.parser.NginxConfParser;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.DirectiveType;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.ServerDirective;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.UpstreamDirective;
import com.jslsolucoes.nginx.admin.repository.ImportRepository;
import com.jslsolucoes.nginx.admin.repository.ServerRepository;
import com.jslsolucoes.nginx.admin.repository.SslCertificateRepository;
import com.jslsolucoes.nginx.admin.repository.StrategyRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostRepository;

import freemarker.template.TemplateException;

@RequestScoped
public class ImportRepositoryImpl implements ImportRepository {

	private ServerRepository serverRepository;
	private UpstreamRepository upstreamRepository;
	private StrategyRepository strategyRepository;
	private VirtualHostRepository virtualHostRepository;
	private SslCertificateRepository sslCertificateRepository;

	public ImportRepositoryImpl() {
		//Default constructor
	}

	@Inject
	public ImportRepositoryImpl(ServerRepository serverRepository, UpstreamRepository upstreamRepository,
			StrategyRepository strategyRepository, VirtualHostRepository virtualHostRepository,
			SslCertificateRepository sslCertificateRepository) {
		this.serverRepository = serverRepository;
		this.upstreamRepository = upstreamRepository;
		this.strategyRepository = strategyRepository;
		this.virtualHostRepository = virtualHostRepository;
		this.sslCertificateRepository = sslCertificateRepository;
	}

	@Override
	public void importFrom(String nginxConf) throws IOException, TemplateException {
		List<Directive> directives = new NginxConfParser(nginxConf).parse();
		servers(directives);
		upstreams(directives);
		virtualHosts(directives);
	}

	private List<Directive> filter(List<Directive> directives, DirectiveType directiveType) {
		return directives.stream().filter(directive -> directive.type().equals(directiveType))
				.collect(Collectors.toList());
	}

	private void virtualHosts(List<Directive> directives) throws IOException, TemplateException {
		for (Directive directive : filter(directives, DirectiveType.SERVER)) {
			ServerDirective serverDirective = (ServerDirective) directive;

			List<VirtualHostAlias> aliases = serverDirective.getAliases().stream()
					.map(alias -> new VirtualHostAlias(alias)).collect(Collectors.toList());

			List<VirtualHostLocation> locations = serverDirective.getLocations().stream()
					.filter(location -> !StringUtils.isEmpty(location.getUpstream()))
					.map(location -> new VirtualHostLocation(location.getPath(),
							upstreamRepository.findByName(location.getUpstream())))
					.collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(aliases) && !CollectionUtils.isEmpty(locations)
					&& virtualHostRepository.hasEquals(new VirtualHost(), aliases) == null) {
				SslCertificate sslCertificate = null;
				if (!StringUtils.isEmpty(serverDirective.getSslCertificate())) {
					OperationResult operationResult = sslCertificateRepository.saveOrUpdate(
							new SslCertificate(UUID.randomUUID().toString()),
							new FileInputStream(new File(serverDirective.getSslCertificate())),
							new FileInputStream(new File(serverDirective.getSslCertificateKey())));
					sslCertificate = new SslCertificate(operationResult.getId());
				}
				virtualHostRepository.saveOrUpdate(
						new VirtualHost(serverDirective.getPort() == 80 ? 0 : 1, sslCertificate), aliases, locations);
			}
		}
	}

	private void upstreams(List<Directive> directives) throws IOException, TemplateException {
		for (Directive directive : filter(directives, DirectiveType.UPSTREAM)) {
			UpstreamDirective upstreamDirective = (UpstreamDirective) directive;
			if (upstreamRepository.hasEquals(new Upstream(upstreamDirective.getName())) == null) {
				upstreamRepository.saveOrUpdate(
						new Upstream(upstreamDirective.getName(),
								strategyRepository.findByName(upstreamDirective.getStrategy())),
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

	@Override
	public List<String> validateBeforeImport(String nginxConf) {
		List<String> errors = new ArrayList<>();

		if (!new File(nginxConf).exists()) {
			errors.add(Messages.getString("import.nginx.conf.invalid", nginxConf));
		}

		return errors;
	}
}
