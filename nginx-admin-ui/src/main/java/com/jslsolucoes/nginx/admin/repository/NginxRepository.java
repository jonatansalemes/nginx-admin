package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.Nginx;

public interface NginxRepository {
	public Nginx nginx();

	public Nginx update(Nginx nginx);

	public List<String> validateBeforeUpdate(Nginx nginx);

	public void insert(Nginx nginx);

}
