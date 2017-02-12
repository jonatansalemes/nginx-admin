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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Smtp;
import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;
import com.jslsolucoes.nginx.admin.repository.InstallRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.SmtpRepository;
import com.jslsolucoes.nginx.admin.repository.UserRepository;

import freemarker.template.TemplateException;

@RequestScoped
public class InstallRepositoryImpl implements InstallRepository {

	private SmtpRepository smtpRepository;
	private NginxRepository nginxRepository;
	private UserRepository userRepository;
	private ConfigurationRepository configurationRepository;

	public InstallRepositoryImpl() {
		//Default constructor
	}

	@Inject
	public InstallRepositoryImpl(SmtpRepository smtpRepository, NginxRepository nginxRepository,
			UserRepository userRepository, ConfigurationRepository configurationRepository) {
		this.smtpRepository = smtpRepository;
		this.nginxRepository = nginxRepository;
		this.userRepository = userRepository;
		this.configurationRepository = configurationRepository;

	}

	@Override
	public List<String> validateBeforeInstall(String login, String loginConfirm, String adminPassword,
			String adminPasswordConfirm, String nginxBin, String nginxSettings, String smtpHost, Integer smtpPort,
			Integer smtpAuthenticate, Integer smtpTls, String smtpFromAddress, String smtpUsername, String smtpPassword,
			String urlBase) {
		List<String> errors = new ArrayList<>();
		errors.addAll(userRepository.validateBeforeCreateAdministrator(login, loginConfirm, adminPassword,
				adminPasswordConfirm));
		errors.addAll(nginxRepository.validateBeforeSaveOrUpdate(new Nginx(nginxBin, nginxSettings)));
		return errors;
	}

	@Override
	public void install(String login, String loginConfirm, String adminPassword, String adminPasswordConfirm,
			String nginxBin, String nginxSettings, String smtpHost, Integer smtpPort, Integer smtpAuthenticate,
			Integer smtpTls, String smtpFromAddress, String smtpUsername, String smtpPassword, String urlBase)
			throws IOException, TemplateException {
		userRepository.createAdministrator(login, adminPassword);
		nginxRepository.saveOrUpdateAndConfigure(new Nginx(nginxBin, nginxSettings));
		smtpRepository.saveOrUpdate(
				new Smtp(smtpHost, smtpPort, smtpAuthenticate, smtpUsername, smtpPassword, smtpTls, smtpFromAddress));
		configurationRepository.update(ConfigurationType.URL_BASE, urlBase);
	}

}
