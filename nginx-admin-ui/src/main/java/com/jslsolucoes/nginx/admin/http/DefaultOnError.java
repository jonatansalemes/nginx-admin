package com.jslsolucoes.nginx.admin.http;

public class DefaultOnError implements OnError {
	public void error(Exception exception) {
		throw new RuntimeException(exception);
	}
}
