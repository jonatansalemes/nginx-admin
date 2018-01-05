package com.jslsolucoes.nginx.admin.os;

public class OperationalSystemInfo {
	private String name;
	private String arch;
	private String version;
	private String distribution;
	private OperationalSystemType operationalSystemType;

	public OperationalSystemInfo(String name, String arch, String version) {
		this.name = name;
		this.arch = arch;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArch() {
		return arch;
	}

	public void setArch(String arch) {
		this.arch = arch;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public OperationalSystemType getOperationalSystemType() {
		return operationalSystemType;
	}

	public void setOperationalSystemType(OperationalSystemType operationalSystemType) {
		this.operationalSystemType = operationalSystemType;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

}
