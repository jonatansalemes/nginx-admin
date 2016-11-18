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

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.annotation.Public;
import com.jslsolucoes.nginx.admin.model.User;
import com.jslsolucoes.nginx.admin.repository.UserRepository;
import com.jslsolucoes.nginx.admin.session.UserSession;
import com.jslsolucoes.nginx.admin.util.HtmlUtil;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
public class UserController {

	private UserSession userSession;
	private Result result;
	private UserRepository userRepository;

	public UserController() {

	}

	@Inject
	public UserController(UserSession userSession, Result result, UserRepository userRepository) {
		this.userSession = userSession;
		this.result = result;
		this.userRepository = userRepository;
	}

	public void logout() {
		this.userSession.logout();
		this.result.redirectTo(this).login();
	}

	@Public
	public void validateReset(String login) {
		this.result.use(Results.json())
				.from(HtmlUtil.convertToUnodernedList(userRepository.validateResetPassword(new User(login))), "errors")
				.serialize();
	}
	
	@Public
	@Post
	public void reset(String login) {
		userRepository.resetPassword(new User(login));
		this.result.include("passwordRecoveryForLogin",login);
		this.result.redirectTo(this).login();
	}

	@Public
	public void resetPassword() {

	}

	@Public
	public void login() {

	}

	@Post
	@Public
	public void authenticate(String login, String password) {
		User user = userRepository.authenticate(new User(login, password));
		if (user != null) {
			userSession.setUser(user);
			this.result.redirectTo(AppController.class).home();
		} else {
			this.result.include("invalid", true);
			this.result.redirectTo(this).login();
		}
	}
}
