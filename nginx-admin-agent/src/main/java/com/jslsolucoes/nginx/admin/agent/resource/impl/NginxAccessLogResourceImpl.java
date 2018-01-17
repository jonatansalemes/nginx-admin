package com.jslsolucoes.nginx.admin.agent.resource.impl;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.RequestScoped;

import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.file.system.FileSystemBuilder;

@RequestScoped
public class NginxAccessLogResourceImpl {
	
	private Logger logger = LoggerFactory.getLogger(NginxAccessLogResourceImpl.class);

	public NginxAccessLogResourceImpl() {

	}
	
	public List<FileObject> collect(String nginxHome) {
		File logFolder = log(nginxHome);
		List<FileObject> files = new ArrayList<>();
		FileSystemBuilder
		.newBuilder()
		.iterate()
			.withDestination(logFolder)
			.withFileFilter(new PrefixFileFilter("access.log."))
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
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		
		FileSystemBuilder
		.newBuilder()
		.iterate()
			.withDestination(logFolder)
			.withFileFilter(new NameFileFilter("access.log"))
			.execute(file -> {
				if (file.length() > sizeLimit()) {
					try {
						atomicInteger.getAndIncrement();
						File toRotate = new File(logFolder, "access.log." + simpleDateFormat.format(new Date()));
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
	
	private File log(String nginxHome){
		return new File(nginxHome,"log");
	}

}
