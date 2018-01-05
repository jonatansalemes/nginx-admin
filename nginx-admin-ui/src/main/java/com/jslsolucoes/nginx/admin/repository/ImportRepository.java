package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;

public interface ImportRepository {

	public List<String> validateBeforeImport(String nginxConf);

	public void importFrom(String nginxConf) throws NginxAdminException;
}
