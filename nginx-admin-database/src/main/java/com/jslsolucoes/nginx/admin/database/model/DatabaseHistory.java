package com.jslsolucoes.nginx.admin.database.model;

public class DatabaseHistory {

	private Long id;
	private String name;
	private Long version;
	
	public DatabaseHistory(Long id,String name,Long version) {
		this.id = id;
		this.name = name;
		this.version = version;
	}
	
	public DatabaseHistory(String name,Long version) {
		this.name = name;
		this.version = version;
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
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
	
	

}
