/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.repository.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

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

	public LogRepositoryImpl() {

	}

	@Inject
	public LogRepositoryImpl(NginxRepository nginxRepository,AccessLogRepository accessLogRepository) {
		this.nginxRepository = nginxRepository;
		this.accessLogRepository = accessLogRepository;
	}

	@Override
	public void collect() {
		Nginx nginx = nginxRepository.configuration();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		FileUtils.iterateFiles(nginx.log(), new String[] { "rotate" }, true).forEachRemaining(file -> {
			try{
				FileUtils
				.readLines(file,"UTF-8")
				.stream()
				.filter(line -> line.trim().startsWith("{") && line.trim().endsWith("}"))
				.forEach(line -> {
					accessLogRepository.log(gson.fromJson(line, AccessLog.class));
				});
				FileUtils.forceDelete(file);
			} catch(Exception exception){
				exception.printStackTrace();
			}
		});
	}

	@Override
	public void rotate() {
		Nginx nginx = nginxRepository.configuration();
		FileUtils.iterateFiles(nginx.log(), new String[] { "log" }, true).forEachRemaining(file -> {
			if (file.length() > sizeLimit()) {
				try {
					FileUtils.copyFile(file,
							new File(nginx.log(), FilenameUtils.getBaseName(file.getName()) + new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".log.rotate"));
					FileUtils.write(file,"","UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private long sizeLimit() {
		return 1 * 1024 * 1024;
	}
}
