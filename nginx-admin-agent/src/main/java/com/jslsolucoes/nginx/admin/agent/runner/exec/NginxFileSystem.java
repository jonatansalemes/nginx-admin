package com.jslsolucoes.nginx.admin.agent.runner.exec;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.nginx.admin.agent.model.response.model.AccessLog;
import com.jslsolucoes.template.TemplateProcessor;

@ApplicationScoped
public class NginxFileSystem {
	
	private Logger logger = LoggerFactory.getLogger(NginxFileSystem.class);

	public NginxFileSystem() {

	}

	public void configure(String nginxHome, Integer maxPostSize,Boolean gzip) {
		createFileSystem(nginxHome);
		createTemplate(nginxHome,maxPostSize,gzip);
	}
	
	public List<AccessLog> collect(String nginxHome) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		List<AccessLog> accessLogs = new ArrayList<>();
		File logFolder = log(nginxHome);
		FileUtils.iterateFiles(logFolder, new String[] { "rotate" }, true).forEachRemaining(file -> {			
				try {
					FileUtils.readLines(file, "UTF-8").stream()
							.filter(line -> isJson(line))
							.forEach(line-> {
								try {
									accessLogs.add(gson.fromJson(line, AccessLog.class));
								} catch (Exception exception) {
									logger.error(line + " could'n be stored ", exception);
								}
							});
					FileSystemBuilder
					.newBuilder()
					.delete()
						.withDestination(file)
						.execute()
					.end();
				} catch (IOException e) {
					logger.error(" could'n read files ", e);
				}
		});
		return accessLogs;
	}
	
	private Boolean isJson(String json) {
		return json.trim().startsWith("{") && json.trim().endsWith("}");
	}

	public void rotate(String nginxHome) {
		File logFolder = log(nginxHome);
		FileUtils.iterateFiles(logFolder, new String[] { "log" }, true).forEachRemaining(file -> {
			if (file.length() > sizeLimit()) {
				try {
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
						.end();
				} catch (Exception iOException) {
					logger.error("Could not rotate file", iOException);
				}
			}
		});
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
