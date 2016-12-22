package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "virtual_host_alias", schema = "admin")
public class VirtualHostAlias implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "alias")
	private String alias;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_virtual_host")
	private VirtualHost virtualHost;

	public VirtualHostAlias() {

	}
	
	public VirtualHostAlias(String alias) {
		this.alias = alias;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VirtualHost getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(VirtualHost virtualHost) {
		this.virtualHost = virtualHost;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
