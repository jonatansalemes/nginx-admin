package com.jslsolucoes.nginx.admin.agent.model;

import java.io.File;
import java.util.Date;

public class FileObjectBuilder {

	private Date lastModified;
	private String name;
	private Long size;
	private String content;
	private String charset = "UTF-8";

	private FileObjectBuilder() {

	}

	public static FileObjectBuilder newBuilder() {
		return new FileObjectBuilder();
	}

	public FileObjectBuilder from(File file) {
		this.lastModified = new Date(file.lastModified());
		this.name = file.getName();
		this.size = file.length();
		return this;
	}

	public FileObjectBuilder withContent(String content) {
		this.content = content;
		return this;
	}

	public FileObjectBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public FileObjectBuilder withCharset(String charset) {
		this.charset = charset;
		return this;
	}

	public FileObjectBuilder withSize(Long size) {
		this.size = size;
		return this;
	}

	public FileObject build() {
		FileObject fileObject = new FileObject();
		fileObject.setLastModified(lastModified);
		fileObject.setFileName(name);
		fileObject.setSize(size);
		fileObject.setCharset(charset);
		fileObject.setContent(content);
		return fileObject;
	}
}
