package com.jslsolucoes.nginx.admin.database.repository.impl.driver;

public class H2DriverQuery extends GenericDriverQuery {

	public H2DriverQuery() {
		super("/db/h2");
	}
}
