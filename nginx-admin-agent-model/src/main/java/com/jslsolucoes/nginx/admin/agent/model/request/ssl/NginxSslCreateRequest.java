package com.jslsolucoes.nginx.admin.agent.model.request.ssl;

import com.jslsolucoes.nginx.admin.agent.model.FileObject;

public class NginxSslCreateRequest {

	private FileObject fileObject;
	private String uuid;

	public NginxSslCreateRequest() {

	}

	public NginxSslCreateRequest(String uuid, FileObject fileObject) {
		this.fileObject = fileObject;
		this.uuid = uuid;
	}

	public FileObject getFileObject() {
		return fileObject;
	}

	public void setFileObject(FileObject fileObject) {
		this.fileObject = fileObject;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
