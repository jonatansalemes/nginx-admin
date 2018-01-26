package com.jslsolucoes.nginx.admin.agent.model.response.error.log;

import java.util.List;

import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxErrorLogCollectResponse implements NginxResponse {

	private List<FileObject> files;
	
	public NginxErrorLogCollectResponse() {
		
	}
	
	public NginxErrorLogCollectResponse(List<FileObject> files) {
		this.files = files;
	}

	public List<FileObject> getFiles() {
		return files;
	}

	public void setFiles(List<FileObject> files) {
		this.files = files;
	}

	

}
