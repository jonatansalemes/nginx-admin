package com.jslsolucoes.nginx.admin.repository.impl;

public enum ConfigurationType {
	DB_VERSION("DB_VERSION"), URL_BASE("URL_BASE");

	private String variable;

	ConfigurationType(String variable) {
		this.variable = variable;
	}

	public String getVariable() {
		return variable;
	}

}
