package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.io.File;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.nginx.admin.agent.config.Configuration;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.FileObjectBuilder;

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
	
	
	private NginxOperationResult createOrUpdate(String uuid,FileObject fileObject) {
		try {
			FileSystemBuilder
				.newBuilder()
				.write()
					.withDestination(ssl(uuid))
					.withCharset("UTF-8")
					.withContent(fileObject.getContent())
					.execute()
				.end();
			
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,e);
		}
	}
	
	public NginxOperationResult update(String uuid,FileObject fileObject) {
		return createOrUpdate(uuid, fileObject);
	}
	
	public NginxOperationResult create(String uuid,FileObject fileObject) {
		return createOrUpdate(uuid, fileObject);
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

	public FileObject read(String uuid) {
		try {
			File ssl = ssl(uuid);
			String content = FileSystemBuilder
					.newBuilder()
					.read()
						.withDestination(ssl)
						.withCharset("UTF-8")
						.execute();
			return FileObjectBuilder
					.newBuilder()
					.from(ssl)
						.withCharset("UTF-8")
						.withContent(content)
					.build();
		} catch (Exception e) {
			return null;
		}
	}
}
