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

	@Column(name = "settings")
	private String settings;
	
	@Column(name = "gzip")
	private Integer gzip;
	
	@Column(name = "max_post_size")
	private Integer maxPostSize;

	public Nginx() {

	}
	
	public Nginx(String bin, String home) {
		this(null, bin, home,1,100);
	}

	public Nginx(Long id, String bin, String settings,Integer gzip,Integer maxPostSize) {
		this.id = id;
		this.bin = bin;
		this.settings = settings;
		this.gzip = (gzip == null ? 0 : gzip);
		this.maxPostSize = maxPostSize;
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
	
	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	
	public File binFolder(){
		return bin().getParentFile();
	}
	
	public File bin(){
		return new File(bin);
	}
	
	public File conf(){
		return new File(settings, "nginx.conf");
	}
	
	public File pid(){
		return new File(settings, "nginx.pid");
	}

	public File ssl() {
		return new File(settings, "ssl");
	}

	public File upstream() {
		return new File(settings, "upstream");
	}
	
	public File virtualDomain() {
		return new File(settings, "virtual-domain");
	}

	public File setting() {
		return new File(settings);
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

	

}
