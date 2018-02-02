package com.jslsolucoes.nginx.admin.database.repository;

import com.jslsolucoes.nginx.admin.database.model.DatabaseHistory;

public interface DatabaseHistoryRepository {
	public Boolean exists(String database,String table);
	public void create(String database,String table);
	public DatabaseHistory current(String database,String table);
	public void insert(String database, String table, String name, Long version);
	public void init(String database);
}
