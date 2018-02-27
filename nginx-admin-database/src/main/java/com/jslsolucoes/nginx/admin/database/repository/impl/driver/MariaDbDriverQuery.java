package com.jslsolucoes.nginx.admin.database.repository.impl.driver;

public class MariaDbDriverQuery extends GenericDriverQuery {

	public MariaDbDriverQuery() {
		super("/db/mariadb");
	}
}
