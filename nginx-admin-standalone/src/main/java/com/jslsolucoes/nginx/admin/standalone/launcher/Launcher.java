package com.jslsolucoes.nginx.admin.standalone.launcher;

public class Launcher {

	private String home;
	private Integer port;
	private Boolean quit;
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public Boolean getQuit() {
		return quit;
	}
	public void setQuit(Boolean quit) {
		this.quit = quit;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
}
