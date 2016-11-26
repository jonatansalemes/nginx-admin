package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.User;

public interface AppRepository {

	public List<String> checkAllRequiredConfiguration(User user);

}
