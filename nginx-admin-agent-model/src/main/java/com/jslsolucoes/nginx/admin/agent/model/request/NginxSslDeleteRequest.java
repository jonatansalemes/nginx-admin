package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxSslDeleteRequest {

	private String certificateUuid;
	private String certificatePrivateKeyUuid;
	
	public NginxSslDeleteRequest() {
		
	}
	
	public NginxSslDeleteRequest(String certificateUuid,String certificatePrivateKeyUuid) {
		this.certificateUuid = certificateUuid;
		this.certificatePrivateKeyUuid = certificatePrivateKeyUuid;
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
