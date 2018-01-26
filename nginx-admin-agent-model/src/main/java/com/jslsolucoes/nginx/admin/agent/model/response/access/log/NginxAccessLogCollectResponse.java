package com.jslsolucoes.nginx.admin.agent.model.response.access.log;

import java.util.List;

import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxAccessLogCollectResponse implements NginxResponse {

	private List<FileObject> files;
	
	public NginxAccessLogCollectResponse() {
		
	}
	
	public NginxAccessLogCollectResponse(List<FileObject> files) {
		this.files = files;
	}

	public List<FileObject> getFiles() {
		return files;
	}

	public void setFiles(List<FileObject> files) {
		this.files = files;
	}

	

}
