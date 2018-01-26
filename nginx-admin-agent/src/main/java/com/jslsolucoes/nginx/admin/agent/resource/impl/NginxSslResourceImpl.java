package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.nginx.admin.agent.config.Configuration;

@RequestScoped
public class NginxSslResourceImpl {
	
	
	private Configuration configuration;

	@Deprecated
	public NginxSslResourceImpl() {

	}
	
	@Inject
	public NginxSslResourceImpl(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public NginxOperationResult delete(String uuid) {
		try {
			FileSystemBuilder
				.newBuilder()
				.delete()
					.withDestination(ssl(uuid))
					.execute()
				.end();
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,e);
		}
	}
	
	public NginxOperationResult create(String certificate,String certificateUuid,String certificatePrivateKey,
			String certificatePrivateKeyUuid) {
		try {
			File sslFolder = sslFolder();
			
			FileSystemBuilder
				.newBuilder()
				.write()
					.withDestination(new File(sslFolder,certificateUuid + ".ssl"))
					.withCharset("UTF-8")
					.withContent(decode(certificate, "UTF-8"))
					.execute()
				.end()
				.write()
					.withDestination(new File(sslFolder,certificatePrivateKeyUuid + ".ssl"))
					.withCharset("UTF-8")
					.withContent(decode(certificatePrivateKey, "UTF-8"))
					.execute()
				.end();
			
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,e);
		}
	}
	
	private String decode(String value,String charset) throws UnsupportedEncodingException {
		return new String(Base64.getDecoder().decode(value.getBytes(charset)));
	}
	
	private File ssl(String uuid) {
		return new File(sslFolder(),uuid + ".ssl");
	}
	
	private File sslFolder() {
		return new File(settings(),"ssl");
	}
	
	private String settings() {
		return configuration.getNginx().getSetting();
	}
}
