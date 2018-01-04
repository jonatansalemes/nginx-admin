package com.jslsolucoes.nginx.admin.standalone.config;

public enum DatabaseDriver {
	ORACLE("oracle"),H2("h2");
	
	private String driverName;

	private DatabaseDriver(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverName() {
		return driverName;
	}

	public static DatabaseDriver forName(String driverName){
		for(DatabaseDriver databaseDriver : values()){
			if(databaseDriver.getDriverName().equals(driverName)) {
				return databaseDriver;
			}
		}
		throw new RuntimeException("Could not select database driver");
	}
}
