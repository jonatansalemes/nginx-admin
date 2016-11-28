package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.ArrayList;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.User;
import com.jslsolucoes.nginx.admin.repository.AppRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.SmtpRepository;
import com.jslsolucoes.nginx.admin.repository.UserRepository;

@RequestScoped
public class AppRepositoryImpl implements AppRepository {

	private SmtpRepository smtpRepository;
	private NginxRepository nginxRepository;
	private UserRepository userRepository;

	public AppRepositoryImpl() {

	}

	@Inject
	public AppRepositoryImpl(SmtpRepository smtpRepository, NginxRepository nginxRepository,
			UserRepository userRepository) {
		this.smtpRepository = smtpRepository;
		this.nginxRepository = nginxRepository;
		this.userRepository = userRepository;

	}

	@Override
	public List<String> checkAllRequiredConfiguration(User user) {
		List<String> errors = new ArrayList<String>();
		user = userRepository.load(user);
		if (smtpRepository.smtp() == null) {
			errors.add(Messages.getString("invalid.configuration.smtp"));
		}
		if (nginxRepository.nginx() == null) {
			errors.add(Messages.getString("invalid.configuration.nginx"));
		}
		if (StringUtils.equals(user.getLogin(), "admin@localhost.com")) {
			errors.add(Messages.getString("invalid.configuration.login"));
		}
		if (StringUtils.equals(user.getPassword(), DigestUtils.sha256Hex("password"))) {
			errors.add(Messages.getString("invalid.configuration.password"));
		}
		return errors;
	}
}
