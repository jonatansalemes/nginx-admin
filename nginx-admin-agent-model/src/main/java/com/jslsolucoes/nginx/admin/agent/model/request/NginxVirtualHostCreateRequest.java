package com.jslsolucoes.nginx.admin.agent.model.request;

import java.util.List;

import com.jslsolucoes.nginx.admin.agent.model.Location;

public class NginxVirtualHostCreateRequest {

	private String home;
	private String uuid;
	private Boolean https;
	private String certificateUuid;
	private String certificatePrivateKeyUuid;
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

	public String getCertificateUuid() {
		return certificateUuid;
	}

	public void setCertificateUuid(String certificateUuid) {
		this.certificateUuid = certificateUuid;
	}

	public String getCertificatePrivateKeyUuid() {
		return certificatePrivateKeyUuid;
	}

	public void setCertificatePrivateKeyUuid(String certificatePrivateKeyUuid) {
		this.certificatePrivateKeyUuid = certificatePrivateKeyUuid;
	}

}
