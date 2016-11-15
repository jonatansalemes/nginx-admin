package com.jslsolucoes.nginx.admin.repository;

import com.jslsolucoes.nginx.admin.model.User;

public interface UserRepository {
	public User authenticate(User user);
}
