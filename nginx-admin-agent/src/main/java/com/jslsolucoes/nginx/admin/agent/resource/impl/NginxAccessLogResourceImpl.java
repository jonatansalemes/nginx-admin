package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.nginx.admin.agent.config.Configuration;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.FileObjectBuilder;

@RequestScoped
public class NginxAccessLogResourceImpl {

	private Logger logger = LoggerFactory.getLogger(NginxAccessLogResourceImpl.class);
	private Configuration configuration;

	@Deprecated
	public NginxAccessLogResourceImpl() {

	}

	@Inject
	public NginxAccessLogResourceImpl(Configuration configuration) {
		this.configuration = configuration;
	}

	public List<FileObject> collect() {
		File log = log();
		List<FileObject> files = new ArrayList<>();
		FileSystemBuilder.newBuilder().iterate().withDestination(log)
				.withFileFilter(new PrefixFileFilter("access.log.")).execute(file -> {
					FileSystemBuilder.newBuilder().read().withDestination(file).withCharset("UTF-8")
							.execute(content -> {
								FileObject fileObject = FileObjectBuilder.newBuilder().from(file).withCharset("UTF-8")
										.withContent(content).build();
								files.add(fileObject);
							}).end().delete().withDestination(file).execute().end();
				}).end();
		return files;
	}

	public Integer rotate() {
		AtomicInteger atomicInteger = new AtomicInteger(0);
		File log = log();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

		FileSystemBuilder.newBuilder().iterate().withDestination(log).withFileFilter(new NameFileFilter("access.log"))
				.execute(file -> {
					if (file.length() > sizeLimit()) {
						try {
							atomicInteger.getAndIncrement();
							File toRotate = new File(log, "access.log." + simpleDateFormat.format(new Date()));
							FileSystemBuilder.newBuilder().copy().withSource(file).withDestination(toRotate).execute()
									.end().write().withDestination(file).withContent("").withCharset("UTF-8").execute()
									.end();
						} catch (Exception exception) {
							logger.error("Could not rotate file", exception);
						}
					}
				}).end();
		return atomicInteger.get();
	}

	private long sizeLimit() {
		return 1L * 1024L * 1024L;
	}

	private File log() {
		return new File(settings(), "log");
	}

	private String settings() {
		return configuration.getNginx().getSetting();
	}

}
