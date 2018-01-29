package com.jslsolucoes.nginx.admin.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.ui.config.Configuration;
import com.jslsolucoes.nginx.admin.ui.config.DatabaseDriver;

import br.com.caelum.vraptor.jpa.DefaultEntityManagerConfiguration;

@Specializes
@ApplicationScoped
public class OverrideEntityManagerConfiguration extends DefaultEntityManagerConfiguration {

	@Inject
	private Configuration configuration;

	@Override
	public Map<String, Object> properties() {
		Map<String, Object> properties = new HashMap<>();
		System.out.println(dialect());
		properties.put("hibernate.dialect", dialect());
		return properties;
	}

	public String dialect() {
		DatabaseDriver databaseDriver = configuration.getDatabase().getDatabaseDriver();
		if (databaseDriver.equals(DatabaseDriver.H2)) {
			return "org.hibernate.dialect.H2Dialect";
		} else if (databaseDriver.equals(DatabaseDriver.MYSQL)) {
			return "org.hibernate.dialect.MySQL5Dialect";
		} else if (databaseDriver.equals(DatabaseDriver.ORACLE)) {
			return "org.hibernate.dialect.Oracle10gDialect";
		} else if (databaseDriver.equals(DatabaseDriver.POSTGRESQL)) {
			return "org.hibernate.dialect.PostgreSQLDialect";
		}  else if (databaseDriver.equals(DatabaseDriver.SQLSERVER)) {
			return "org.hibernate.dialect.SQLServer2008Dialect";
		}
		throw new RuntimeException("Could not find dialect for driver " + databaseDriver.getDriverName());
	}
}
