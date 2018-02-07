package com.jslsolucoes.nginx.admin.agent.model.response.ssl;

import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxSslReadResponse implements NginxResponse {

	private FileObject fileObject;

	public NginxSslReadResponse() {

	}

	public NginxSslReadResponse(FileObject fileObject) {
		this.fileObject = fileObject;
	}

	public FileObject getFileObject() {
		return fileObject;
	}

	public void setFileObject(FileObject fileObject) {
		this.fileObject = fileObject;
	}

}
