package com.jslsolucoes.nginx.admin.model;

public class VirtualDomain {

	private String domain;
	
	private Integer https;
	
	private Integer port;
	
	private SslCertificate sslCertificate;
	
	private Upstream upstream;
	
}
