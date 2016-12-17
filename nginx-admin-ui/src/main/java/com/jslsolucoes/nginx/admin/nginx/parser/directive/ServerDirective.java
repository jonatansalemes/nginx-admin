package com.jslsolucoes.nginx.admin.nginx.parser.directive;

import java.util.List;

public class ServerDirective implements Directive {

	private Integer port;
	private List<String> aliases;
	private String sslCertificate;
	private String sslCertificateKey;
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public List<String> getAliases() {
		return aliases;
	}
	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}
	public String getSslCertificate() {
		return sslCertificate;
	}
	public void setSslCertificate(String sslCertificate) {
		this.sslCertificate = sslCertificate;
	}
	public String getSslCertificateKey() {
		return sslCertificateKey;
	}
	public void setSslCertificateKey(String sslCertificateKey) {
		this.sslCertificateKey = sslCertificateKey;
	}
	
	@Override
	public DirectiveType type() {
		return DirectiveType.SERVER;
	}
	
}
