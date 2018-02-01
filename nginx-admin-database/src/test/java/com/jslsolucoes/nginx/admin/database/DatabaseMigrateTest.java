package com.jslsolucoes.nginx.admin.database;

import java.util.UUID;

import org.junit.Test;

public class DatabaseMigrateTest {

	@Test
	public void migrate() {
		DatabaseMigrate
			.newBuilder()
			.withDriver("h2")
			.withUrlConnection("jdbc:h2:~/h2/" + UUID.randomUUID().toString())
			.withUsername("sa")
			.withPassword("sa")
			.migrate();
	}
}
