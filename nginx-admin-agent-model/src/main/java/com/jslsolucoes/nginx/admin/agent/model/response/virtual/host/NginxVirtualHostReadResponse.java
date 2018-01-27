package com.jslsolucoes.nginx.admin.agent.model.response.virtual.host;

import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxVirtualHostReadResponse implements NginxResponse {

	private FileObject fileObject;

	public NginxVirtualHostReadResponse() {

	}

	public NginxVirtualHostReadResponse(FileObject fileObject) {
		this.fileObject = fileObject;
	}

	public FileObject getFileObject() {
		return fileObject;
	}

	public void setFileObject(FileObject fileObject) {
		this.fileObject = fileObject;
	}

}
