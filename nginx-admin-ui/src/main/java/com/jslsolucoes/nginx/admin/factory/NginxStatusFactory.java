package com.jslsolucoes.nginx.admin.factory;

import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import com.jslsolucoes.nginx.admin.nginx.status.NginxStatus;
import com.jslsolucoes.nginx.admin.nginx.status.NginxStatusReader;

@RequestScoped
public class NginxStatusFactory {

	@Produces
	public NginxStatus getInstance() throws SQLException {
		return new NginxStatusReader().status();
	}
}
