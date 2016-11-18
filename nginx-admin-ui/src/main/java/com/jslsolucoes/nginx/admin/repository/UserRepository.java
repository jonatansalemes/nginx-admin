package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.User;

public interface UserRepository {
	public User authenticate(User user);

	public List<String> validateResetPassword(User user);

	public void resetPassword(User user);
}
