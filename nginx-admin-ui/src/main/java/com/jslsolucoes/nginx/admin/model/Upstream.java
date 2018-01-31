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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "upstream", schema = "admin")
@SequenceGenerator(name = "upstream_sq", initialValue = 1, schema = "admin", allocationSize = 1, sequenceName = "admin.upstream_sq")
public class Upstream implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="upstream_sq")
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_strategy")
	private Strategy strategy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nginx")
	private Nginx nginx;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_resource_identifier")
	private ResourceIdentifier resourceIdentifier;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "upstream")
	private Set<UpstreamServer> servers;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "upstream")
	private Set<VirtualHostLocation> virtualHostLocations;

	public Upstream() {
		// default constructor
	}

	public Upstream(Long id, String name, Strategy strategy, ResourceIdentifier resourceIdentifier, Nginx nginx) {
		this.id = id;
		this.name = name;
		this.strategy = strategy;
		this.resourceIdentifier = resourceIdentifier;
		this.nginx = nginx;
	}

	public Upstream(Long id) {
		this.id = id;
	}

	public Upstream(String name, Strategy strategy, Nginx nginx) {
		this.name = name;
		this.strategy = strategy;
		this.nginx = nginx;
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

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public Set<UpstreamServer> getServers() {
		return servers;
	}

	public ResourceIdentifier getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(ResourceIdentifier resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

	public Set<VirtualHostLocation> getVirtualHostLocations() {
		return virtualHostLocations;
	}

	public Nginx getNginx() {
		return nginx;
	}

	public void setNginx(Nginx nginx) {
		this.nginx = nginx;
	}
}
