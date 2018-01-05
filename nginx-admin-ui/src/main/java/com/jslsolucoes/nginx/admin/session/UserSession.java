package com.jslsolucoes.nginx.admin.session;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.jslsolucoes.nginx.admin.model.User;
import com.jslsolucoes.vraptor4.auth.model.AuthFunctionality;
import com.jslsolucoes.vraptor4.auth.model.AuthUser;
import com.jslsolucoes.vraptor4.auth.model.AuthUserSession;

@SuppressWarnings("serial")
@SessionScoped
@Named
public class UserSession implements Serializable,AuthUserSession {

	private User user;

	public void logout() {
		this.user = null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public List<AuthFunctionality> authFunctionalities() {
		return null;
	}

	@Override
	public AuthUser user() {
		return user;
	}
}
