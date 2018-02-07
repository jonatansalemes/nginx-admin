package com.jslsolucoes.nginx.admin.agent.model.request.ssl;

import com.jslsolucoes.nginx.admin.agent.model.FileObject;

public class NginxSslUpdateRequest {

	private FileObject fileObject;

	public NginxSslUpdateRequest() {

	}

	public NginxSslUpdateRequest(FileObject fileObject) {
		this.fileObject = fileObject;
	}

	public FileObject getFileObject() {
		return fileObject;
	}

	public void setFileObject(FileObject fileObject) {
		this.fileObject = fileObject;
	}
}
