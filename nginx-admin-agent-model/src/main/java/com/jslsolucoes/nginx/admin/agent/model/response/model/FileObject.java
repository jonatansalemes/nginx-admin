package com.jslsolucoes.nginx.admin.agent.model.response.model;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

public class FileObject {

	private String fileName;

	private Date lastModified;

	private Long size;
	
	private String content;

	public FileObject() {

	}

	public FileObject(String fileName, Date lastModified, Long size) {
		this.fileName = fileName;
		this.lastModified = lastModified;
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getDecoded(String charset) {
		try {
			return String.valueOf(Base64.getDecoder().decode(content.getBytes(charset)));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public void encode(String content,String charset) {
		try {
			this.content = Base64.getEncoder().encodeToString(content.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
