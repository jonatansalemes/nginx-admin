package com.jslsolucoes.nginx.admin.agent.model.response;

import java.util.List;

import com.jslsolucoes.nginx.admin.agent.model.FileObject;

public class NginxLogCollectResponse implements NginxResponse {

	private List<FileObject> files;
	
	public NginxLogCollectResponse() {
		
	}
	
	public NginxLogCollectResponse(List<FileObject> files) {
		this.files = files;
	}

	public List<FileObject> getFiles() {
		return files;
	}

	public void setFiles(List<FileObject> files) {
		this.files = files;
	}

	

}
