package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxFileSystemPermissionRequest {

	private String path;
	
	public NginxFileSystemPermissionRequest() {
		
	}
	
	public NginxFileSystemPermissionRequest(String path) {
		this.path = path;	
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
