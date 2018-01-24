package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nginx")
	private Nginx nginx;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_resource_identifier_certificate")
	private ResourceIdentifier resourceIdentifierCertificate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_resource_identifier_certificate_private_key")
	private ResourceIdentifier resourceIdentifierCertificatePrivateKey;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sslCertificate")
	private Set<VirtualHost> virtualHosts;

	public SslCertificate() {
		// default constructor
	}

	public SslCertificate(Long id, String commonName, ResourceIdentifier resourceIdentifierCertificate,
			ResourceIdentifier resourceIdentifierCertificatePrivateKey) {
		this.id = id;
		this.commonName = commonName;
		this.resourceIdentifierCertificate = resourceIdentifierCertificate;
		this.resourceIdentifierCertificatePrivateKey = resourceIdentifierCertificatePrivateKey;
	}

	public SslCertificate(Long id) {
		this.id = id;
	}

	public SslCertificate(String commonName) {
		this.commonName = commonName;
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

	public Set<VirtualHost> getVirtualHosts() {
		return virtualHosts;
	}

	public ResourceIdentifier getResourceIdentifierCertificate() {
		return resourceIdentifierCertificate;
	}

	public void setResourceIdentifierCertificate(ResourceIdentifier resourceIdentifierCertificate) {
		this.resourceIdentifierCertificate = resourceIdentifierCertificate;
	}

	public ResourceIdentifier getResourceIdentifierCertificatePrivateKey() {
		return resourceIdentifierCertificatePrivateKey;
	}

	public void setResourceIdentifierCertificatePrivateKey(ResourceIdentifier resourceIdentifierCertificatePrivateKey) {
		this.resourceIdentifierCertificatePrivateKey = resourceIdentifierCertificatePrivateKey;
	}

	public Nginx getNginx() {
		return nginx;
	}

	public void setNginx(Nginx nginx) {
		this.nginx = nginx;
	}

}
