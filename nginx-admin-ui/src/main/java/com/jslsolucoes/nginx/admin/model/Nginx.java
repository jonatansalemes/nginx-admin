package com.jslsolucoes.nginx.admin.model;

import java.io.File;
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

	@Column(name = "bin")
	private String bin;

	@Column(name = "home")
	private String home;

	public Nginx() {

	}

	public Nginx(Long id, String bin, String home) {
		this.id = id;
		this.bin = bin;
		this.home = home;
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

	public File settings() {
		return new File(home, "settings");
	}

	public File ssl() {
		return new File(settings(), "ssl");
	}

}
