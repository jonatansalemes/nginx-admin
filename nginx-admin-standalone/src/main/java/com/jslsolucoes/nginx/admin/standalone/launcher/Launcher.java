package com.jslsolucoes.nginx.admin.standalone.launcher;

public class Launcher {

	private String bind;
	private Integer port;
	private Boolean quit;
	private DataSource dataSource;
	
	public String getBind() {
		return bind;
	}
	public void setBind(String bind) {
		this.bind = bind;
	}
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
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
