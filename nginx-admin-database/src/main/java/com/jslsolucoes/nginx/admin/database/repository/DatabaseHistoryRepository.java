package com.jslsolucoes.nginx.admin.database.repository;

import com.jslsolucoes.nginx.admin.database.model.DatabaseHistory;

public interface DatabaseHistoryRepository {
	public Boolean exists(String schema,String tableName);
	public void create(String schema,String tableName);
	public DatabaseHistory last(String schema,String tableName);
}
