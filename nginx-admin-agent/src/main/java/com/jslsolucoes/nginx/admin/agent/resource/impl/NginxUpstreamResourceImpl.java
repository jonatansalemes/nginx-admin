package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.io.File;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.nginx.admin.agent.model.Endpoint;
import com.jslsolucoes.template.TemplateProcessor;

@RequestScoped
public class NginxUpstreamResourceImpl {
	
	
	public NginxUpstreamResourceImpl() {

	}
	
	private File upstream(String nginxHome) {
		return new File(nginxHome,"upstream");
	}
	
	
	public NginxOperationResult create(String nginxHome,String name,String uuid,String strategy,List<Endpoint> endpoints) {
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
	
	
}
