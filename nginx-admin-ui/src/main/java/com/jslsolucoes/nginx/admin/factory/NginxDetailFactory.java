package com.jslsolucoes.nginx.admin.factory;

import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.nginx.detail.NginxDetail;
import com.jslsolucoes.nginx.admin.nginx.detail.NginxDetailReader;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

public class NginxDetailFactory {

	@Inject
	private Runner runner;
	
	@Inject
	private NginxRepository nginxRepository;
	
	@Produces
	@RequestScoped
	public NginxDetail getInstance() throws SQLException {
		return new NginxDetailReader(runner, nginxRepository.configuration())
				.details();
	}
}
