package com.jslsolucoes.nginx.admin.os;

public enum OperationalSystemDistribution {
	WINDOWS("Microsoft"),CENTOS("CentOs"),MAC("Apple"),DARWIN("Apple"),UNKNOW_DISTRIBUTION("Unknow");
	
	private String name;

	OperationalSystemDistribution(String name){
		this.name = name;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}