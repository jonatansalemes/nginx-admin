package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxSslDeleteRequest {

	private String home;
	private String certificateUuid;
	private String certificatePrivateKeyUuid;
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
