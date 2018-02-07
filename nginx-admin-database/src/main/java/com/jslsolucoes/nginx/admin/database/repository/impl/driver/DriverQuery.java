package com.jslsolucoes.nginx.admin.database.repository.impl.driver;

public interface DriverQuery {

	public String exists(String database, String table);
	
	public String create(String database, String table);

	public String current(String database, String table);

	public String insert(String database, String table);

	public String init(String database);
}
