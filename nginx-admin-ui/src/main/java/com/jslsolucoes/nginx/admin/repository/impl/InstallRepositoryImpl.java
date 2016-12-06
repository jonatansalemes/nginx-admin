package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Smtp;
import com.jslsolucoes.nginx.admin.repository.InstallRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.SmtpRepository;
import com.jslsolucoes.nginx.admin.repository.UserRepository;

@RequestScoped
public class InstallRepositoryImpl implements InstallRepository {

	private SmtpRepository smtpRepository;
	private NginxRepository nginxRepository;
	private UserRepository userRepository;

	public InstallRepositoryImpl() {

	}

	@Inject
	public InstallRepositoryImpl(SmtpRepository smtpRepository, NginxRepository nginxRepository,
			UserRepository userRepository) {
		this.smtpRepository = smtpRepository;
		this.nginxRepository = nginxRepository;
		this.userRepository = userRepository;

	}

	@Override
	public List<String> validateBeforeInstall(String login, String loginConfirm, String adminPassword,
			String adminPasswordConfirm, String nginxBin, String nginxSettings, String smtpHost,
			Integer smtpPort, Integer smtpAuthenticate, Integer smtpTls, String smtpFromAddress, String smtpUsername,
			String smtpPassword) {
		List<String> errors = new ArrayList<String>();
		errors.addAll(userRepository.validateBeforeCreateAdministrator(login,loginConfirm,adminPassword,adminPasswordConfirm));
		errors.addAll(smtpRepository.validateBeforeSaveOrUpdate(new Smtp(smtpHost, smtpPort, smtpAuthenticate, smtpUsername, smtpPassword, smtpTls, smtpFromAddress)));
		errors.addAll(nginxRepository.validateBeforeSaveOrUpdate(new Nginx(nginxBin,nginxSettings)));
		return errors;
	}

	@Override
	public void install(String login, String loginConfirm, String adminPassword, String adminPasswordConfirm,
			String nginxBin, String nginxSettings, String smtpHost, Integer smtpPort, Integer smtpAuthenticate,
			Integer smtpTls, String smtpFromAddress, String smtpUsername, String smtpPassword) {
		userRepository.createAdministrator(login, adminPassword);
		nginxRepository.saveOrUpdate(new Nginx(nginxBin,nginxSettings));
		smtpRepository.saveOrUpdate(new Smtp(smtpHost, smtpPort, smtpAuthenticate, smtpUsername, smtpPassword, smtpTls, smtpFromAddress));
	}

}
