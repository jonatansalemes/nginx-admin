package com.jslsolucoes.nginx.admin.database;

import java.sql.SQLException;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseMigrateTest {
	
	
	
	@Before
	public void before() throws SQLException {
		
	}

	@Test
	public void migrate() throws SQLException {
			
		String uuid = UUID.randomUUID().toString();
		String jdbcUrl = "jdbc:h2:~/h2/" + uuid + ";AUTO_SERVER=TRUE";
		DatabaseMigrate
			.newBuilder()
			.withDriver("h2")
			.withUrlConnection(jdbcUrl)
			.withUsername("sa")
			.withPassword("sa")
			.withClasspath("/db","/db2")
			.migrate()
			.migrate();
	}
	
	@After
	public void after() {
		
	}
}
