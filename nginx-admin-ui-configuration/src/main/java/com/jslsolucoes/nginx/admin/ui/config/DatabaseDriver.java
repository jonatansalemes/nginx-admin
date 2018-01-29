package com.jslsolucoes.nginx.admin.ui.config;

public enum DatabaseDriver {
	ORACLE("oracle"), H2("h2"),MYSQL("mysql"),POSTGRESQL("postgresql"),SQLSERVER("sqlserver");

	private String driverName;

	private DatabaseDriver(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverName() {
		return driverName;
	}

	public static DatabaseDriver forName(String driverName) {
		for (DatabaseDriver databaseDriver : values()) {
			if (databaseDriver.getDriverName().equals(driverName)) {
				return databaseDriver;
			}
		}
		throw new RuntimeException("Could not select database driver");
	}
}
