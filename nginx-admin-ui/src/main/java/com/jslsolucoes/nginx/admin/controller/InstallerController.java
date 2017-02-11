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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.jslsolucoes.nginx.admin.annotation.Application;
import com.jslsolucoes.nginx.admin.annotation.Public;
import com.jslsolucoes.nginx.admin.html.HtmlUtil;
import com.jslsolucoes.nginx.admin.repository.InstallRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("installer")
@Public
public class InstallerController {

	private Result result;
	private InstallRepository installRepository;
	private Properties properties;
	private HttpServletRequest httpServletRequest;

	public InstallerController() {
		this(null, null, null, null);
	}

	@Inject
	public InstallerController(@Application Properties properties, Result result, InstallRepository installRepository,
			HttpServletRequest httpServletRequest) {
		this.result = result;
		this.properties = properties;
		this.installRepository = installRepository;
		this.httpServletRequest = httpServletRequest;
	}

	public void form() throws MalformedURLException {
		this.result.include("version", properties.get("app.version"));
		this.result.include("urlBase", urlBase());
	}

	private String urlBase() throws MalformedURLException {
		URL url = new URL(httpServletRequest.getRequestURL().toString());
		return url.getProtocol() + "://" + url.getHost() + ":" + url.getPort()
				+ ("/".equals(httpServletRequest.getServletContext().getContextPath()) ? ""
						: httpServletRequest.getServletContext().getContextPath());
	}

	@Post
	public void validateBeforeInstall(String login, String loginConfirm, String adminPassword,
			String adminPasswordConfirm, String nginxBin, String nginxSettings, String smtpHost, Integer smtpPort,
			Integer smtpAuthenticate, Integer smtpTls, String smtpFromAddress, String smtpUsername,
			String smtpPassword,String urlBase) {
		this.result.use(Results.json())
				.from(HtmlUtil.convertToUnodernedList(installRepository.validateBeforeInstall(login, loginConfirm,
						adminPassword, adminPasswordConfirm, nginxBin, nginxSettings, smtpHost, smtpPort,
						smtpAuthenticate, smtpTls, smtpFromAddress, smtpUsername, smtpPassword,urlBase)), "errors")
				.serialize();
	}

	@Post
	public void install(String login, String loginConfirm, String adminPassword, String adminPasswordConfirm,
			String nginxBin, String nginxSettings, String smtpHost, Integer smtpPort, Integer smtpAuthenticate,
			Integer smtpTls, String smtpFromAddress, String smtpUsername, String smtpPassword,String urlBase) {
		installRepository.install(login, loginConfirm, adminPassword, adminPasswordConfirm, nginxBin, nginxSettings,
				smtpHost, smtpPort, smtpAuthenticate, smtpTls, smtpFromAddress, smtpUsername, smtpPassword,urlBase);
		this.result.redirectTo(UserController.class).login();
	}

}
