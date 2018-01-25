package com.jslsolucoes.nginx.admin.repository.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jslsolucoes.nginx.admin.model.AccessLog;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.AccessLogRepository;
import com.jslsolucoes.nginx.admin.repository.LogRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class LogRepositoryImpl implements LogRepository {

	private NginxRepository nginxRepository;
	private AccessLogRepository accessLogRepository;
	private static Logger logger = LoggerFactory.getLogger(LogRepositoryImpl.class);

	@Deprecated
	public LogRepositoryImpl() {
		
	}

	@Inject
	public LogRepositoryImpl(NginxRepository nginxRepository, AccessLogRepository accessLogRepository) {
		this.nginxRepository = nginxRepository;
		this.accessLogRepository = accessLogRepository;
	}

	@Override
	public void collect() {
		/*
		Nginx nginx = nginxRepository.configuration();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		FileUtils.iterateFiles(nginx.log(), new String[] { "rotate" }, true).forEachRemaining(file -> {
			try {
				FileUtils.readLines(file, "UTF-8").stream()
						.filter(line -> line.trim().startsWith("{") && line.trim().endsWith("}")).forEach(line -> {
							try {
								accessLogRepository.log(gson.fromJson(line, AccessLog.class));
							} catch (Exception exception) {
								logger.error(line + " could'n be stored ", exception);
							}
						});
				FileUtils.forceDelete(file);
			} catch (IOException iOException) {
				logger.error("Could not collect file log", iOException);
			}
		});
		*/
	}

	@Override
	public void rotate() {
		
	}

}
