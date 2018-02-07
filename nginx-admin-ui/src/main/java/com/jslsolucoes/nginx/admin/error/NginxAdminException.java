package com.jslsolucoes.nginx.admin.error;

@SuppressWarnings("serial")
public class NginxAdminException extends Exception {

	public NginxAdminException(Exception exception) {
		super(exception);
	}

}
