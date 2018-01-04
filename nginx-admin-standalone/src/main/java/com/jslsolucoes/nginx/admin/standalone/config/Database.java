package com.jslsolucoes.nginx.admin.standalone.config;

public class Database {

	private DatabaseDriver databaseDriver;
	private String host;
	private Integer port;
	private String sid;
	private String location;
	private String name;
	private String userName;
	private String password;
	private DatabasePool databasePool;

	public DatabaseDriver getDatabaseDriver() {
		return databaseDriver;
	}

	public void setDatabaseDriver(DatabaseDriver databaseDriver) {
		this.databaseDriver = databaseDriver;
	}

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

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConnectionUrl() {
		if (databaseDriver.equals(DatabaseDriver.H2)) {
			return "jdbc:h2:" + location + ";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
		}
		throw new RuntimeException("Could not build connection database url");
	}

}
