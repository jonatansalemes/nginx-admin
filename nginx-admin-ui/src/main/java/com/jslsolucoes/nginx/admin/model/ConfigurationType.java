package com.jslsolucoes.nginx.admin.model;

public enum ConfigurationType {
	DB_VERSION("DB_VERSION"), APP_CONFIGURE("APP_CONFIGURE");

	private String variable;

	ConfigurationType(String variable) {
		this.variable = variable;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}
}
