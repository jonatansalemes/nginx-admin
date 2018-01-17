package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.io.File;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.nginx.admin.agent.model.Endpoint;
import com.jslsolucoes.nginx.admin.agent.model.Location;
import com.jslsolucoes.template.TemplateProcessor;

@RequestScoped
public class NginxFileSystemResourceImpl {
	
	
	public NginxFileSystemResourceImpl() {

	}

	public NginxOperationResult configure(String nginxHome, Integer maxPostSize,Boolean gzip) {
		try {
			createFileSystem(nginxHome);
			createTemplate(nginxHome,maxPostSize,gzip);
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,ExceptionUtils.getFullStackTrace(e));
		}
	}

	private void createTemplate(String nginxHome, Integer maxPostSize,Boolean gzip) {
		TemplateProcessor
		.newBuilder()
			.withTemplate("/template/nginx/dynamic", "root.tpl")
			.withData("nginxHome", nginxHome)
			.withOutputLocation(new File(virtualHost(nginxHome), "root.conf"))
		.process()
		.clear()
			.withTemplate("/template/nginx/dynamic", "nginx.tpl")
			.withData("nginxHome", nginxHome)
			.withData("gzip", gzip)
			.withData("maxPostSize", maxPostSize)
			.withOutputLocation(new File(nginxHome, "nginx.conf"))
		.process();
	}
	
	private File virtualHost(String nginxHome) {
		return new File(nginxHome,"virtual-host");
	}
	
	private File upstream(String nginxHome) {
		return new File(nginxHome,"upstream");
	}

	private void createFileSystem(String nginxHome) {
		FileSystemBuilder.newBuilder()
		.create()
			.withDestination(nginxHome)
			.execute()
		.end()
		.copy()
			.withClasspathResource("/template/nginx/fixed")
			.withDestination(nginxHome)
			.execute()
		.end();
	}

	public NginxOperationResult upstream(String nginxHome,String name,String uuid,String strategy,List<Endpoint> endpoints) {
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
			return new NginxOperationResult(NginxOperationResultType.ERROR,ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	public NginxOperationResult virtualHost(String nginxHome,String uuid,Boolean https,
			String certificate,String certificatePrivateKey,
			List<String> aliases,List<Location> locations) {
		try {
			File virtualHostFolder = virtualHost(nginxHome);
			TemplateProcessor.newBuilder()
				.withTemplate("/template/nginx/dynamic","virtual-host.tpl")
					.withData("https",  https)
					.withData("certificate",  certificate)
					.withData("certificatePrivateKey",  certificatePrivateKey)
					.withData("nginxHome",  nginxHome)
					.withData("aliases",  aliases)
					.withData("locations",  locations)
					.withOutputLocation(new File(virtualHostFolder, uuid + ".conf"))
					.process();
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,ExceptionUtils.getFullStackTrace(e));
		}
	}
}
