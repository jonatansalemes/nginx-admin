package com.jslsolucoes.nginx.admin.ui.config;

public class Database {

	private String driver;
	private String host;
	private Integer port;
	private String name;
	private String username;
	private String password;
	private DatabasePool databasePool;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DatabasePool getDatabasePool() {
		return databasePool;
	}

	public void setDatabasePool(DatabasePool databasePool) {
		this.databasePool = databasePool;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	
}
