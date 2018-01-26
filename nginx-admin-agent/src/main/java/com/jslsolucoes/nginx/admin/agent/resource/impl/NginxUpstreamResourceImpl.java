package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.io.File;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.nginx.admin.agent.config.Configuration;
import com.jslsolucoes.nginx.admin.agent.model.Endpoint;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.FileObjectBuilder;
import com.jslsolucoes.template.TemplateProcessor;

@RequestScoped
public class NginxUpstreamResourceImpl {
	
	private Configuration configuration;

	@Deprecated
	public NginxUpstreamResourceImpl() {

	}
	
	@Inject
	public NginxUpstreamResourceImpl(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public NginxOperationResult create(String name,String uuid,String strategy,List<Endpoint> endpoints) {
		return createOrUpdate(name, uuid, strategy, endpoints);
	}
	
	private NginxOperationResult createOrUpdate(String name,String uuid,String strategy,List<Endpoint> endpoints) {
		try {
			TemplateProcessor.newBuilder()
				.withTemplate("/template/nginx/dynamic","upstream.tpl")
					.withData("name",  name)
					.withData("strategy",  strategy)
					.withData("endpoints",  endpoints)
					.withOutputLocation(upstream(uuid))
					.process();
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,e);
		}
	}
	
	public NginxOperationResult delete(String uuid) {
		try {
			FileSystemBuilder
				.newBuilder()
				.delete()
					.withDestination(upstream(uuid))
					.execute()
				.end();
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,e);
		}
	}

	public NginxOperationResult update(String uuid, String name, String strategy,
			List<Endpoint> endpoints) {
		return createOrUpdate(name, uuid, strategy, endpoints);
	}

	public FileObject read(String uuid) {
		try {
			File upstream = upstream(uuid);
			String content = FileSystemBuilder
					.newBuilder()
					.read()
						.withDestination(upstream)
						.withCharset("UTF-8")
						.execute();
			return FileObjectBuilder
					.newBuilder()
					.from(upstream)
						.withCharset("UTF-8")
						.withContent(content)
					.build();
			
		} catch (Exception e) {
			return null;
		}
	}
	
	private File upstream(String uuid) {
		return new File(upstreamFolder(),uuid + ".conf");
	}
	
	private File upstreamFolder() {
		return new File(settings(),"upstream");
	}
	
	private String settings() {
		return configuration.getNginx().getSetting();
	}

}
