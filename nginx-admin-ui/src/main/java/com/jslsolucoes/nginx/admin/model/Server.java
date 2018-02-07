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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "server")
@SequenceGenerator(name = "server_sq", initialValue = 1, allocationSize = 1, sequenceName = "server_sq")
public class Server implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "server_sq")
	private Long id;

	@Column(name = "ip")
	private String ip;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nginx")
	private Nginx nginx;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "server")
	private Set<UpstreamServer> upstreamServers;

	public Server() {
		// default constructor
	}

	public Server(Long id) {
		this.id = id;
	}

	public Server(Long id, String ip, Nginx nginx) {
		this.id = id;
		this.ip = ip;
		this.nginx = nginx;
	}

	public Server(String ip, Nginx nginx) {
		this.ip = ip;
		this.nginx = nginx;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<UpstreamServer> getUpstreamServers() {
		return upstreamServers;
	}

	public Nginx getNginx() {
		return nginx;
	}

	public void setNginx(Nginx nginx) {
		this.nginx = nginx;
	}

}
