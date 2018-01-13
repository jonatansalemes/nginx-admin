package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxFileSystemPermissionResponse implements NginxResponse {

	private Boolean hasPermission;
	
	public NginxFileSystemPermissionResponse() {
		
	}
	
	public NginxFileSystemPermissionResponse(Boolean hasPermission) {
		this.hasPermission = hasPermission;
	}

	public Boolean getHasPermission() {
		return hasPermission;
	}

	public void setHasPermission(Boolean hasPermission) {
		this.hasPermission = hasPermission;
	}
}
