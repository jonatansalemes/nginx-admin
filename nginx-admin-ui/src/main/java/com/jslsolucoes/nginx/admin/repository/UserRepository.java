package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.User;

public interface UserRepository {
	public User authenticate(User user);

	public List<String> validateBeforeResetPassword(User user);

	public void resetPassword(User user);

	public List<String> validateBeforeChangePassword(User user, String oldPassword, String password,
			String passwordConfirm);

	public void changePassword(User user, String password);

	public User loadForSession(User user);

	public User load(User user);

	public List<User> listAll();

	public List<String> validateBeforeCreateAdministrator(String login, String loginConfirm, String password,
			String passwordConfirm);

	public void createAdministrator(String login, String password);
}
