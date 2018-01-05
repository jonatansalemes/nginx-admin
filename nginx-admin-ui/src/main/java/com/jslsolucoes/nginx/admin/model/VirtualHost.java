package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

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

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
@Entity
@Table(name = "virtual_host", schema = "admin")
public class VirtualHost implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "https")
	private Integer https;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ssl_certificate")
	private SslCertificate sslCertificate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_resource_identifier")
	private ResourceIdentifier resourceIdentifier;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "virtualHost")
	private Set<VirtualHostAlias> aliases;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "virtualHost")
	private Set<VirtualHostLocation> locations;

	public VirtualHost() {
		// default constructor
	}

	public VirtualHost(Long id) {
		this.id = id;
	}

	public VirtualHost(Long id, Integer https, SslCertificate sslCertificate, ResourceIdentifier resourceIdentifier) {
		this.id = id;
		this.https = https == null ? 0 : https;
		this.sslCertificate = sslCertificate;
		this.resourceIdentifier = resourceIdentifier;
	}

	public VirtualHost(Integer https, SslCertificate sslCertificate) {
		this.https = https;
		this.sslCertificate = sslCertificate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getHttps() {
		return https;
	}

	public void setHttps(Integer https) {
		this.https = https;
	}

	public SslCertificate getSslCertificate() {
		return sslCertificate;
	}

	public void setSslCertificate(SslCertificate sslCertificate) {
		this.sslCertificate = sslCertificate;
	}

	public ResourceIdentifier getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(ResourceIdentifier resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

	public Set<VirtualHostLocation> getLocations() {
		return locations;
	}

	public Set<VirtualHostAlias> getAliases() {
		return aliases;
	}

	public String getFullAliases() {
		return StringUtils.join(aliases.stream().map(VirtualHostAlias::getAlias).collect(Collectors.toSet()), " ");
	}

}
