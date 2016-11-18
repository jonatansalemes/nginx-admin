package com.jslsolucoes.nginx.admin.nginx;

import java.io.File;

public class NginxConfiguration {

	private File bin;
	private File conf;
	private File home;
	
	public NginxConfiguration(File bin,File conf,File home) {
		this.bin = bin;
		this.conf = conf;
		this.home = home;
	}
	
	public File getBin() {
		return bin;
	}
	public void setBin(File bin) {
		this.bin = bin;
	}
	public File getConf() {
		return conf;
	}
	public void setConf(File conf) {
		this.conf = conf;
	}

	public File getHome() {
		return home;
	}

	public void setHome(File home) {
		this.home = home;
	}
}
