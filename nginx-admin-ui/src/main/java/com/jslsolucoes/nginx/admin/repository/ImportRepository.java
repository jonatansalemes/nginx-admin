package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;

public interface ImportRepository {

	public List<String> validateBeforeImport(String nginxConf);

	public void importFrom(Nginx nginx,String nginxConf) throws NginxAdminException;
}
