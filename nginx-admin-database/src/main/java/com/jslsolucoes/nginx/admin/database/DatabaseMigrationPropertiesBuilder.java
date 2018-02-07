package com.jslsolucoes.nginx.admin.database;

import java.util.Properties;

public class DatabaseMigrationPropertiesBuilder {

	private DatabaseMigrationBuilder databaseMigrationBuilder;
	private Properties properties;

	private DatabaseMigrationPropertiesBuilder(DatabaseMigrationBuilder databaseMigrationBuilder,Properties properties) {
		this.databaseMigrationBuilder = databaseMigrationBuilder;
		this.properties = properties;
	}
	
	public DatabaseMigrationPropertiesBuilder withProperty(String key,String value){
		this.properties.put(key, value);
		return this;
	}

	public DatabaseMigrationBuilder end() {
		return databaseMigrationBuilder;
	}

	public static DatabaseMigrationPropertiesBuilder newBuilder(DatabaseMigrationBuilder databaseMigrationBuilder,
			Properties properties) {
		return new DatabaseMigrationPropertiesBuilder(databaseMigrationBuilder, properties);
	}
}
