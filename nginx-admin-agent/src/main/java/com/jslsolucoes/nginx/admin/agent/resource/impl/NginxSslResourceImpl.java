package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.enterprise.context.RequestScoped;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.jslsolucoes.file.system.FileSystemBuilder;

@RequestScoped
public class NginxSslResourceImpl {
	
	
	public NginxSslResourceImpl() {

	}
	
	private File ssl(String nginxHome) {
		return new File(nginxHome,"ssl");
	}
	
	public NginxOperationResult delete(String nginxHome,
			String certificateUuid,
			String certificatePrivateKeyUuid) {
		try {
			File sslFolder = ssl(nginxHome);
			FileSystemBuilder
				.newBuilder()
				.delete()
					.withDestination(new File(sslFolder,certificateUuid + ".cer"))
					.execute()
				.end()
				.delete()
					.withDestination(new File(sslFolder,certificatePrivateKeyUuid + ".key"))
					.execute()
				.end();
			
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	public NginxOperationResult create(String nginxHome,
			String certificate,String certificateUuid,String certificatePrivateKey,
			String certificatePrivateKeyUuid) {
		try {
			File sslFolder = ssl(nginxHome);
			
			FileSystemBuilder
				.newBuilder()
				.write()
					.withDestination(new File(sslFolder,certificateUuid + ".cer"))
					.withCharset("UTF-8")
					.withContent(decode(certificate, "UTF-8"))
					.execute()
				.end()
				.write()
					.withDestination(new File(sslFolder,certificatePrivateKeyUuid + ".key"))
					.withCharset("UTF-8")
					.withContent(decode(certificatePrivateKey, "UTF-8"))
					.execute()
				.end();
			
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	private String decode(String value,String charset) throws UnsupportedEncodingException {
		return new String(Base64.getDecoder().decode(value.getBytes(charset)));
	}
}
