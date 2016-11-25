/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.controller;

import java.io.IOException;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.repository.AppRepository;
import com.jslsolucoes.nginx.admin.repository.UserRepository;
import com.jslsolucoes.nginx.admin.session.UserSession;
import com.jslsolucoes.nginx.admin.util.HtmlUtil;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
public class AppController {

	private Result result;
	private UserSession userSession;
	private UserRepository userRepository;
	private AppRepository appRepository;

	public AppController() {

	}

	@Inject
	public AppController(Result result, UserSession userSession, UserRepository userRepository,
			AppRepository appRepository) {
		this.result = result;
		this.userSession = userSession;
		this.userRepository = userRepository;
		this.appRepository = appRepository;
	}

	@Path(value = { "/", "/app/home" })
	public void home() {

	}

	@Path("/app/reconfigure")
	public void reconfigure() {

	}

	@Path("/app/validateBeforeReconfigure")
	public void validateBeforeReconfigure(String password, String passwordConfirm, String login, String bin,
			String configHome) {
		this.result.use(Results.json())
				.from(HtmlUtil.convertToUnodernedList(appRepository.validateBeforeReconfigure(userSession.getUser(),
						password, passwordConfirm, login, bin, configHome)), "errors")
				.serialize();
	}

	@Path("/app/reconfigured")
	public void reconfigured(String login, String password, String bin, String configHome) throws IOException {
		appRepository.reconfigure(userSession.getUser(), login, password, bin, configHome);
		userSession.setUser(userRepository.loadForSession(userSession.getUser()));
		this.result.redirectTo(this).home();
	}
}
