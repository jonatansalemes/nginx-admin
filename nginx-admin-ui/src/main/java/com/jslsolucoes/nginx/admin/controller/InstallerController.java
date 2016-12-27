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
		this.result.use(Results.json()).from(userRepository.listAll()).serialize();
	}

	public void form() {

	}

	@Post
	public void validateBeforeInstall(String login, String loginConfirm, String adminPassword,
			String adminPasswordConfirm, String nginxBin, String nginxSettings, String smtpHost,
			Integer smtpPort, Integer smtpAuthenticate, Integer smtpTls, String smtpFromAddress, String smtpUsername,
			String smtpPassword) {
		this.result.use(Results.json())
				.from(HtmlUtil.convertToUnodernedList(installRepository.validateBeforeInstall(login, loginConfirm,
						adminPassword, adminPasswordConfirm, nginxBin, nginxSettings, smtpHost, smtpPort,
						smtpAuthenticate, smtpTls, smtpFromAddress, smtpUsername, smtpPassword)), "errors")
				.serialize();
	}

	@Post
	public void install(String login, String loginConfirm, String adminPassword, String adminPasswordConfirm,
			String nginxBin, String nginxSettings, String smtpHost, Integer smtpPort, Integer smtpAuthenticate,
			Integer smtpTls, String smtpFromAddress, String smtpUsername, String smtpPassword) {
		installRepository.install(login, loginConfirm, adminPassword, adminPasswordConfirm, nginxBin, nginxSettings,
				smtpHost, smtpPort, smtpAuthenticate, smtpTls, smtpFromAddress, smtpUsername, smtpPassword);
		this.result.redirectTo(UserController.class).login();
	}

}
