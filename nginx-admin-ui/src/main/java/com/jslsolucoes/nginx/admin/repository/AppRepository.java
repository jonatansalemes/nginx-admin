package com.jslsolucoes.nginx.admin.repository;

import java.io.IOException;
import java.util.List;

import com.jslsolucoes.nginx.admin.model.User;

public interface AppRepository {

	public List<String> validateBeforeReconfigure(User user, String password, String passwordConfirm, String login,
			String bin, String config);

	public void reconfigure(User user, String login, String password, String bin, String configHome) throws IOException;
}
