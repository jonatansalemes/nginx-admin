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
@Table(name = "upstream", schema = "admin")
public class Upstream implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_strategy")
	private Strategy strategy;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_resource_identifier")
	private ResourceIdentifier resourceIdentifier;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="upstream")
	private Set<UpstreamServer> servers;
	
	public Upstream() {
	
	}
	
	public Upstream(Long id,String name,Strategy strategy,ResourceIdentifier resourceIdentifier) {
		this.id = id;
		this.name = name;
		this.strategy = strategy;
		this.resourceIdentifier = resourceIdentifier;
	}
	
	public Upstream(Long id) {
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

}
