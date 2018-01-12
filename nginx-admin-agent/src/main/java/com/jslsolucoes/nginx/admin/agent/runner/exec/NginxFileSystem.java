package com.jslsolucoes.nginx.admin.agent.runner.exec;

import java.io.File;

import javax.enterprise.context.ApplicationScoped;

import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.template.TemplateProcessor;

@ApplicationScoped
public class NginxFileSystem {

	public NginxFileSystem() {

	}

	public void configure(String nginxHome, Integer maxPostSize,Boolean gzip) {
		createFileSystem(nginxHome);
		createTemplate(nginxHome,maxPostSize,gzip);
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

	private void createFileSystem(String nginxHome) {
		FileSystemBuilder.newBuilder()
		.create()
			.withDestination(nginxHome)
			.makeDirectory()
		.end()
		.copy()
			.withClasspathResource("/template/nginx/fixed")
			.withDestination(nginxHome)
			.copy()
		.end();
	}


}
