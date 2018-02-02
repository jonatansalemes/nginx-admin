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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "virtual_host_location")
@SequenceGenerator(name = "virtual_host_location_sq", initialValue = 1, allocationSize = 1, sequenceName = "virtual_host_location_sq")
public class VirtualHostLocation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "virtual_host_location_sq")
	private Long id;

	@Column(name = "path")
	private String path;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_upstream")
	private Upstream upstream;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_virtual_host")
	private VirtualHost virtualHost;

	public VirtualHostLocation() {
		// default constructor
	}

	public VirtualHostLocation(String path, Upstream upstream) {
		this.path = path;
		this.upstream = upstream;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Upstream getUpstream() {
		return upstream;
	}

	public void setUpstream(Upstream upstream) {
		this.upstream = upstream;
	}

	public VirtualHost getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(VirtualHost virtualHost) {
		this.virtualHost = virtualHost;
	}

}
