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
@Table(name = "upstream_server")
@SequenceGenerator(name = "upstream_server_sq", initialValue = 1, allocationSize = 1, sequenceName = "upstream_server_sq")
public class UpstreamServer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "upstream_server_sq")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_upstream")
	private Upstream upstream;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_server")
	private Server server;

	@Column(name = "port")
	private Integer port;

	public UpstreamServer() {
		// default constructor
	}

	public UpstreamServer(Server server, Integer port) {
		this.server = server;
		this.port = port;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Upstream getUpstream() {
		return upstream;
	}

	public void setUpstream(Upstream upstream) {
		this.upstream = upstream;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}
