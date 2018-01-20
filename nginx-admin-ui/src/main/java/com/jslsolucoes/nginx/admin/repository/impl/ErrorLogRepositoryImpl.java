package com.jslsolucoes.nginx.admin.repository.impl;

import java.io.File;
import com.jslsolucoes.i18n.Messages;
import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.ErrorLogRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class ErrorLogRepositoryImpl implements ErrorLogRepository {
	
	private NginxRepository nginxRepository;
	
	public ErrorLogRepositoryImpl() {
		
	}

	@Inject
	public ErrorLogRepositoryImpl(NginxRepository nginxRepository) {
		this.nginxRepository = nginxRepository;
	}

	@Override
	public String content() {
		/*
		Nginx nginx = nginxRepository.configuration();
		File file = new File(nginx.log(),"error.log");
		if(file.exists()){
			try {
				return FileUtils.readFileToString(file,"UTF-8");
			} catch (IOException e) {
				return Messages.getString("error.log.read.error",e.getMessage());
			}
		} else {
			return Messages.getString("error.log.not.found",file.getAbsolutePath());
		}
		*/
		return "";
	}

}
