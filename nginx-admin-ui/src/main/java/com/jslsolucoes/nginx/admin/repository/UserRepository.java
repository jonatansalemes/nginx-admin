package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.User;

public interface UserRepository {
	public User authenticate(String identification, String password);

	public List<String> validateBeforeResetPasswordFor(String identification);

	public String resetPasswordFor(String identification);

	public List<String> validateBeforeChangePassword(User user, String oldPassword, String password,
			String passwordConfirm);

	public void changePassword(User user, String password);

	public User loadForSession(User user);

	public User load(User user);

	public List<User> listAll();

	public List<String> validateBeforeCreateUser(String login, String loginConfirm, String email, String password,
			String passwordConfirm);

	public void create(String login, String email, String password);
}
