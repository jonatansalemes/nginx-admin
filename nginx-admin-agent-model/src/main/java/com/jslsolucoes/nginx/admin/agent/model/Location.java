package com.jslsolucoes.nginx.admin.agent.model;

public class Location {
	private String path;
	private String upstream;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUpstream() {
		return upstream;
	}

	public void setUpstream(String upstream) {
		this.upstream = upstream;
	}

}
