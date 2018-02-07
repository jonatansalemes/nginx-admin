package com.jslsolucoes.nginx.admin.nginx.parser.directive;

import java.util.List;

public class VirtualHostDirective implements Directive {

	private Integer port;
	private List<String> aliases;
	private SslDirective sslCertificate;
	private SslDirective sslCertificateKey;
	private List<LocationDirective> locations;

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

	public List<LocationDirective> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationDirective> locations) {
		this.locations = locations;
	}

	@Override
	public DirectiveType type() {
		return DirectiveType.VIRTUAL_HOST;
	}

	public SslDirective getSslCertificate() {
		return sslCertificate;
	}

	public void setSslCertificate(SslDirective sslCertificate) {
		this.sslCertificate = sslCertificate;
	}

	public SslDirective getSslCertificateKey() {
		return sslCertificateKey;
	}

	public void setSslCertificateKey(SslDirective sslCertificateKey) {
		this.sslCertificateKey = sslCertificateKey;
	}

}
