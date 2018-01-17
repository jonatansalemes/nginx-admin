package com.jslsolucoes.nginx.admin.agent.model.request;

import java.util.List;

import com.jslsolucoes.nginx.admin.agent.model.Location;

public class NginxVirtualHostRequest {

	private String home;
	private String uuid;
	private Boolean https;
	private String certificate;
	private String certificatePrivateKey;
	private List<String> aliases;
	private List<Location> locations;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Boolean getHttps() {
		return https;
	}

	public void setHttps(Boolean https) {
		this.https = https;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getCertificatePrivateKey() {
		return certificatePrivateKey;
	}

	public void setCertificatePrivateKey(String certificatePrivateKey) {
		this.certificatePrivateKey = certificatePrivateKey;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

}
