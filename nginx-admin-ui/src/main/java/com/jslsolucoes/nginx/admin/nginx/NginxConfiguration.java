package com.jslsolucoes.nginx.admin.nginx;

import java.io.File;

public class NginxConfiguration {

	private File bin;
	private File conf;
	
	public NginxConfiguration(File bin,File conf) {
		this.bin = bin;
		this.conf = conf;
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
}
