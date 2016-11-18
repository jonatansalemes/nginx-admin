package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.User;

public interface UserRepository {
	public User authenticate(User user);

	public List<String> validateBeforeResetPassword(User user);

	public void resetPassword(User user);
	
	public void changePassword(User user, String password);

	public List<String> validateBeforeChangePassword(User user, String password, String passwordConfirm);

	public User loadForSession(User user);
}
