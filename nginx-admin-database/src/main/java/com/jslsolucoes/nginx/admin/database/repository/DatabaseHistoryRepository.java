package com.jslsolucoes.nginx.admin.database.repository;

import com.jslsolucoes.nginx.admin.database.model.DatabaseHistory;

public interface DatabaseHistoryRepository {
	public Boolean exists(String schema,String tableName);
	public void create(String schema,String tableName);
	public DatabaseHistory current(String schema,String tableName);
	public void insert(String schema, String tableName, String name, Long version);
}
