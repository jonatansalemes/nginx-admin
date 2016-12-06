package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ssl_certificate", schema = "admin")
public class SslCertificate implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "common_name")
	private String commonName;

	@Column(name = "certificate")
	private String certificate;

	@Column(name = "certificate_private_key")
	private String certificatePrivateKey;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="sslCertificate")
	private Set<VirtualDomain> virtualDomains;

	public SslCertificate() {

	}

	public SslCertificate(Long id, String commonName, String certificate, String certificatePrivateKey) {
		this.id = id;
		this.commonName = commonName;
		this.certificate = certificate;
		this.certificatePrivateKey = certificatePrivateKey;
	}

	public SslCertificate(Long id) {
		this.id = id;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCertificatePrivateKey() {
		return certificatePrivateKey;
	}

	public void setCertificatePrivateKey(String certificatePrivateKey) {
		this.certificatePrivateKey = certificatePrivateKey;
	}

	public Set<VirtualDomain> getVirtualDomains() {
		return virtualDomains;
	}

}
