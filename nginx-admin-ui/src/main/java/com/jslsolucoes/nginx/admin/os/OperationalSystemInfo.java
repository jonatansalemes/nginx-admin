package com.jslsolucoes.nginx.admin.os;

public class OperationalSystemInfo {
	private String name;
	private String arch;
	private String version;
	private OperationalSystemDistribution operationalSystemDistribution;
	
	public OperationalSystemInfo(String name,String arch,String version){
		this(name, arch, version, OperationalSystemDistribution.UNKNOW_DISTRIBUTION);
	}
	
	public OperationalSystemInfo(String name,String arch,String version,OperationalSystemDistribution operationalSystemDistribution) {
		this.name = name;
		this.arch = arch;
		this.version = version;
		this.operationalSystemDistribution = operationalSystemDistribution;
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

	public OperationalSystemDistribution getOperationalSystemDistribution() {
		return operationalSystemDistribution;
	}

	public void setOperationalSystemDistribution(OperationalSystemDistribution operationalSystemDistribution) {
		this.operationalSystemDistribution = operationalSystemDistribution;
	}
	
}
