package com.jslsolucoes.nginx.admin.agent.model;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

public class FileObject {

	private String fileName;

	private Date lastModified;

	private Long size;

	private String content;

	private String charset = "UTF-8";

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
		try {
			return new String(Base64.getDecoder().decode(content.getBytes(charset)));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public void setContent(String content) {
		try {
			this.content = Base64.getEncoder().encodeToString(content.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}
