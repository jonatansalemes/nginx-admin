package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.io.File;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.nginx.admin.agent.model.Endpoint;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.FileObjectBuilder;
import com.jslsolucoes.template.TemplateProcessor;

@RequestScoped
public class NginxUpstreamResourceImpl {
	
	
	public NginxUpstreamResourceImpl() {

	}
	
	private File upstream(String nginxHome) {
		return new File(nginxHome,"upstream");
	}
	
	public NginxOperationResult create(String nginxHome,String name,String uuid,String strategy,List<Endpoint> endpoints) {
		return createOrUpdate(nginxHome, name, uuid, strategy, endpoints);
	}
	
	private NginxOperationResult createOrUpdate(String nginxHome,String name,String uuid,String strategy,List<Endpoint> endpoints) {
		try {
			File upstreamFolder = upstream(nginxHome);
			TemplateProcessor.newBuilder()
				.withTemplate("/template/nginx/dynamic","upstream.tpl")
					.withData("name",  name)
					.withData("strategy",  strategy)
					.withData("endpoints",  endpoints)
					.withOutputLocation(new File(upstreamFolder, uuid + ".conf"))
					.process();
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,e);
		}
	}
	
	public NginxOperationResult delete(String nginxHome,
			String uuid) {
		try {
			File upstreamFolder = upstream(nginxHome);
			FileSystemBuilder
				.newBuilder()
				.delete()
					.withDestination(new File(upstreamFolder,uuid + ".conf"))
					.execute()
				.end();
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,e);
		}
	}

	public NginxOperationResult update(String uuid, String nginxHome, String name, String strategy,
			List<Endpoint> endpoints) {
		return createOrUpdate(nginxHome, name, uuid, strategy, endpoints);
	}

	public FileObject read(String nginxHome, String uuid) {
		try {
			File upstreamFolder = upstream(nginxHome);
			File file = new File(upstreamFolder,uuid + ".conf");
			
			String content = FileSystemBuilder
					.newBuilder()
					.read()
						.withDestination(file)
						.withCharset("UTF-8")
						.execute();
			
			return FileObjectBuilder
					.newBuilder()
					.from(file)
						.withCharset("UTF-8")
						.withContent(content)
						.withEncoded(true)
					.build();
			
		} catch (Exception e) {
			return null;
		}
	}

}
