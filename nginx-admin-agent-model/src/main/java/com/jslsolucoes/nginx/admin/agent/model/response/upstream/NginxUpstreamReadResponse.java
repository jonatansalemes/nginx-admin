package com.jslsolucoes.nginx.admin.agent.model.response.upstream;

import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxUpstreamReadResponse implements NginxResponse {

	private FileObject fileObject;
	
	public NginxUpstreamReadResponse() {
		
	}
	
	public NginxUpstreamReadResponse(FileObject fileObject) {
		this.fileObject = fileObject;
	}

	public FileObject getFileObject() {
		return fileObject;
	}

	public void setFileObject(FileObject fileObject) {
		this.fileObject = fileObject;
	}
	
}
