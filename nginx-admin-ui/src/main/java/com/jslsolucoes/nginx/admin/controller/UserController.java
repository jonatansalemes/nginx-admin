package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.User;
import com.jslsolucoes.nginx.admin.repository.UserRepository;
import com.jslsolucoes.nginx.admin.session.UserSession;
import com.jslsolucoes.tagria.lib.form.FormValidation;
import com.jslsolucoes.vraptor4.auth.annotation.Public;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
public class UserController {

	private UserSession userSession;
	private Result result;
	private UserRepository userRepository;

	@Deprecated
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

	public void validateBeforeChangePassword(String passwordOld, String password, String passwordConfirm) {
		this.result.use(Results.json())
				.from(FormValidation.newBuilder().toUnordenedList(userRepository
						.validateBeforeChangePassword(userSession.getUser(), passwordOld, password, passwordConfirm)),
						"errors")
				.serialize();
	}

	public void changePassword(boolean forced) {
		this.result.include("forced", forced);
	}

	@Post
	public void change(String password, boolean forced) {
		userRepository.changePassword(userSession.getUser(), password);
		this.result.include("passwordChanged", true);
		if (forced) {
			this.result.redirectTo(AppController.class).home();
		} else {
			this.result.redirectTo(this).changePassword(false);
		}
	}

	@Public
	public void validateBeforeResetPassword(String identification) {
		this.result.use(Results.json())
				.from(FormValidation.newBuilder()
						.toUnordenedList(userRepository.validateBeforeResetPasswordFor(identification)), "errors")
				.serialize();
	}

	@Public
	public void resetPassword() {

	}

	@Public
	@Post
	public void reset(String identification) {
		String emailAddress = userRepository.resetPasswordFor(identification);
		this.result.include("passwordRecovery", true);
		this.result.include("passwordRecoveryForEmailAddress", emailAddress);
		this.result.redirectTo(this).login();
	}

	@Public
	public void login() {

	}

	@Post
	@Public
	public void authenticate(String identification, String password) {
		User user = userRepository.authenticate(identification, password);
		if (user != null) {
			userSession.setUser(userRepository.loadForSession(user));
			if (user.getPasswordForceChange() == 1) {
				this.result.redirectTo(this).changePassword(true);
			} else {
				this.result.redirectTo(AppController.class).home();
			}
		} else {
			this.result.include("authenticationFailed", true);
			this.result.redirectTo(this).login();
		}
	}

}
