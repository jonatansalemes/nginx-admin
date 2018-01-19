package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxSslCreateRequest {

	private String home;
	private String certificate;
	private String certificateUuid;
	private String certificatePrivateKey;
	private String certificatePrivateKeyUuid;
	
	public NginxSslCreateRequest() {
		
	}

	public NginxSslCreateRequest(String home, String certificate, String certificateUuid, String certificatePrivateKey,
			String certificatePrivateKeyUuid) {
		this.home = home;
		this.certificate = certificate;
		this.certificateUuid = certificateUuid;
		this.certificatePrivateKey = certificatePrivateKey;
		this.certificatePrivateKeyUuid = certificatePrivateKeyUuid;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getCertificateUuid() {
		return certificateUuid;
	}

	public void setCertificateUuid(String certificateUuid) {
		this.certificateUuid = certificateUuid;
	}

	public String getCertificatePrivateKey() {
		return certificatePrivateKey;
	}

	public void setCertificatePrivateKey(String certificatePrivateKey) {
		this.certificatePrivateKey = certificatePrivateKey;
	}

	public String getCertificatePrivateKeyUuid() {
		return certificatePrivateKeyUuid;
	}

	public void setCertificatePrivateKeyUuid(String certificatePrivateKeyUuid) {
		this.certificatePrivateKeyUuid = certificatePrivateKeyUuid;
	}

}
