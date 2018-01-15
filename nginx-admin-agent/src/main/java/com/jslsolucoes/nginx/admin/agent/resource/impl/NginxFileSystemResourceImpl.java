package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.nginx.admin.agent.model.response.model.FileObject;
import com.jslsolucoes.template.TemplateProcessor;

@ApplicationScoped
public class NginxFileSystemResourceImpl {
	
	private Logger logger = LoggerFactory.getLogger(NginxFileSystemResourceImpl.class);

	public NginxFileSystemResourceImpl() {

	}

	public void configure(String nginxHome, Integer maxPostSize,Boolean gzip) {
		createFileSystem(nginxHome);
		createTemplate(nginxHome,maxPostSize,gzip);
	}
	
	public List<FileObject> collect(String nginxHome) {
		File logFolder = log(nginxHome);
		List<FileObject> files = new ArrayList<>();
		FileSystemBuilder
		.newBuilder()
		.iterate()
			.withDestination(logFolder)
			.withFileFilter(new SuffixFileFilter("rotate"))
			.execute(file -> {
						FileSystemBuilder
								.newBuilder()
								.read()
									.withDestination(file)
									.withCharset("UTF-8")
									.execute(content -> {
											FileObject fileObject = new FileObject();
											fileObject.setLastModified(new Date(file.lastModified()));
											fileObject.setFileName(file.getName());
											fileObject.setSize(file.length());
											fileObject.encode(content,"UTF-8");
											files.add(fileObject);
									})
						.end()
						.delete()
							.withDestination(file)
							.execute()
						.end();		
			})
		.end();	
		return files;
	}	

	public Integer rotate(String nginxHome) {
		AtomicInteger atomicInteger = new AtomicInteger(0);
		File logFolder = log(nginxHome);
		
		FileSystemBuilder
		.newBuilder()
		.iterate()
			.withDestination(logFolder)
			.withFileFilter(new SuffixFileFilter("log"))
			.execute(file -> {
				if (file.length() > sizeLimit()) {
					try {
						atomicInteger.getAndIncrement();
						File toRotate = new File(logFolder, FilenameUtils.getBaseName(file.getName())
								+ new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".log.rotate");
						FileSystemBuilder
							.newBuilder()
							.copy()
								.withSource(file)
								.withDestination(toRotate)
								.execute()
							.end()
							.write()
								.withDestination(file)
								.withContent("")
								.withCharset("UTF-8")
								.execute()
							.end();
					} catch (Exception exception) {
						logger.error("Could not rotate file", exception);
					}
				}
			})
		.end();
		return atomicInteger.get();
	}
	
	private long sizeLimit() {
		return 1L * 1024L * 1024L;
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
	
	private File log(String nginxHome){
		return new File(nginxHome,"log");
	}
	
	private File virtualHost(String nginxHome) {
		return new File(nginxHome,"virtual-host");
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
	
	public String content(String path){
		return FileSystemBuilder.newBuilder()
			.read()
				.withDestination(path)
				.withCharset("UTF-8")
				.execute();
	}
	
	public boolean hasPermissionToWrite(String path) {
		try {
			FileSystemBuilder.newBuilder()
			.create()
				.withDestination(path)
				.execute()
			.end()
			.touch()
				.withDestination(new File(path, "touch.txt"))
				.execute()
			.end();
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

}
