package com.jslsolucoes.nginx.admin.ui.standalone.database;

import org.flywaydb.core.Flyway;

import com.jslsolucoes.nginx.admin.ui.config.Configuration;

public class DatabaseMigrate {

	public static void main(String[] args) {
		 Flyway flyway = new Flyway();
		 flyway.setSchemas("admin");
		 flyway.setDataSource("jdbc:h2:~/h2/nginx-admin", "sa", "sa");
	     flyway.setLocations("/db/migration/h2");
	     flyway.migrate();
	}
	
	public void migrate(Configuration configuration) {
		 Flyway flyway = new Flyway();
		 flyway.setLocations();
	     flyway.setDataSource(configuration.getDatabase().getUrlConnection(), configuration.getDatabase().getUserName(), configuration.getDatabase().getPassword());
	     flyway.migrate();
	}
}
