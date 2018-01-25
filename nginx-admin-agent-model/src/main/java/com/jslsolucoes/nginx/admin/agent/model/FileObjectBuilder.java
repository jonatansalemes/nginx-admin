package com.jslsolucoes.nginx.admin.agent.model;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

public class FileObjectBuilder {

	private Date lastModified;
	private String name;
	private Long size;
	private String content;
	private Boolean encoded = Boolean.FALSE;
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
	
	public FileObjectBuilder withEncoded(Boolean encoded) {
		this.encoded = encoded;
		return this;
	}
	
	public FileObject build() {
		FileObject fileObject = new FileObject();
		fileObject.setLastModified(lastModified);
		fileObject.setFileName(name);
		fileObject.setSize(size);
		fileObject.setContent(content());
		return fileObject;
	}
	
	private String content() {
		if(encoded) { 
			try {
				return Base64.getEncoder().encodeToString(content.getBytes(charset));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		} else {
			return content;
		}
	}
}
