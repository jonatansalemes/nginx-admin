package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "nginx", schema = "admin")
public class Nginx implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "bin")
	private String bin;

	@Column(name = "home")
	private String home;
	
	@Column(name = "ip")
	private String ip;
	
	@Column(name = "port")
	private Integer port;

	@Column(name = "gzip")
	private Integer gzip;

	@Column(name = "max_post_size")
	private Integer maxPostSize;
	
	@Column(name = "authorization_key")
	private String authorizationKey;

	public Nginx() {
	
	}

	public Nginx(Long id,String name, String bin, String home,String ip,Integer port, Integer gzip, Integer maxPostSize,
			String authorizationKey) {
		this.id = id;
		this.bin = bin;
		this.name = name;
		this.home = home;
		this.ip = ip;
		this.port = port;
		this.authorizationKey = authorizationKey;
		this.gzip = gzip == null ? 0 : gzip;
		this.maxPostSize = maxPostSize;
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

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getGzip() {
		return gzip;
	}

	public void setGzip(Integer gzip) {
		this.gzip = gzip;
	}

	public Integer getMaxPostSize() {
		return maxPostSize;
	}

	public void setMaxPostSize(Integer maxPostSize) {
		this.maxPostSize = maxPostSize;
	}

	public String getAuthorizationKey() {
		return authorizationKey;
	}

	public void setAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
