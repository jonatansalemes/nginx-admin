package com.jslsolucoes.nginx.admin.database.repository.impl.driver;

public interface DriverQuery {

	public String exists(String schema, String tableName);
	
	public String create(String schema, String tableName);

	public String last(String schema, String tableName);
}
