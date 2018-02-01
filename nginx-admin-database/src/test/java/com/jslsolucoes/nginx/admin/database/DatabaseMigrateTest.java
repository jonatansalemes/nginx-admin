package com.jslsolucoes.nginx.admin.database;

import java.util.UUID;

import org.junit.Test;

public class DatabaseMigrateTest {

	@Test
	public void migrate() {
		
		String uuid = UUID.randomUUID().toString();
		String jdbcUrl = "jdbc:h2:tcp://localhost/~/h2/" + uuid;
		DatabaseMigrate
			.newBuilder()
			.withDriver("h2")
			.withUrlConnection(jdbcUrl)
			.withUsername("sa")
			.withPassword("sa")
			.migrate()
			.withUrlConnection(jdbcUrl)
			.migrate();
	}
}
