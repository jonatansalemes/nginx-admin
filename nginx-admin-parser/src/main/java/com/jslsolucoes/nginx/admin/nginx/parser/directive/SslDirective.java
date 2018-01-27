package com.jslsolucoes.nginx.admin.nginx.parser.directive;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class SslDirective {

	private String commonName;
	private String content;
	private String charset = "UTF-8";

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getContent() {
		try {
			return new String(Base64.getDecoder().decode(content.getBytes(charset)));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public void setContent(String value) {
		try {
			this.content = Base64.getEncoder().encodeToString(value.getBytes(charset));
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
