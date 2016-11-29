package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.annotation.Public;
import com.jslsolucoes.nginx.admin.repository.InstallRepository;
import com.jslsolucoes.nginx.admin.repository.UserRepository;
import com.jslsolucoes.nginx.admin.util.HtmlUtil;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("installer")
@Public
public class InstallerController {

	private UserRepository userRepository;
	private Result result;
	private InstallRepository installRepository;

	public InstallerController() {

	}

	@Inject
	public InstallerController(Result result, InstallRepository installRepository,UserRepository userRepository) {
		this.result = result;
		this.installRepository = installRepository;
		this.userRepository = userRepository;
	}

	public void check() {
		this.result.use(Results.json()).from(userRepository.hasUsers(), "hasUsers").serialize();
	}

	public void form() {

	}

	@Post
	public void validateBeforeInstall(String login, String loginConfirm, String adminPassword,
			String adminPasswordConfirm, String nginxBin, String nginxHome, String smtpHost,
			Integer smtpPort, Integer smtpAuthenticate, Integer smtpTls, String smtpFromAddress, String smtpUsername,
			String smtpPassword) {
		this.result.use(Results.json())
				.from(HtmlUtil.convertToUnodernedList(installRepository.validateBeforeInstall(login, loginConfirm,
						adminPassword, adminPasswordConfirm, nginxBin, nginxHome, smtpHost, smtpPort,
						smtpAuthenticate, smtpTls, smtpFromAddress, smtpUsername, smtpPassword)), "errors")
				.serialize();
	}

	@Post
	public void install(String login, String loginConfirm, String adminPassword, String adminPasswordConfirm,
			String nginxBin, String nginxHome, String smtpHost, Integer smtpPort, Integer smtpAuthenticate,
			Integer smtpTls, String smtpFromAddress, String smtpUsername, String smtpPassword) {
		installRepository.install(login, loginConfirm, adminPassword, adminPasswordConfirm, nginxBin, nginxHome,
				smtpHost, smtpPort, smtpAuthenticate, smtpTls, smtpFromAddress, smtpUsername, smtpPassword);
		this.result.redirectTo(UserController.class).login();
	}

}
