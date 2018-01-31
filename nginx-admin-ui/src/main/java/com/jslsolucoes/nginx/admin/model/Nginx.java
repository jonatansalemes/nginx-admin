package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "nginx", schema = "admin")
@SequenceGenerator(name = "nginx_sq", initialValue = 1, schema = "admin", allocationSize = 1, sequenceName = "admin.nginx_sq")
public class Nginx implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="nginx_sq")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "endpoint")
	private String endpoint;

	@Column(name = "authorization_key")
	private String authorizationKey;
	
	@OneToOne(fetch=FetchType.LAZY,mappedBy="nginx")
	private Configuration configuration;

	public Nginx() {

	}

	public Nginx(Long id, String name, String endpoint, String authorizationKey) {
		this.id = id;
		this.name = name;
		this.endpoint = endpoint;
		this.authorizationKey = authorizationKey;
	}

	public Nginx(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAuthorizationKey() {
		return authorizationKey;
	}

	public void setAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

}
