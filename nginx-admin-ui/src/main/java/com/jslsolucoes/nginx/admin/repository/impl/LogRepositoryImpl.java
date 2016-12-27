package com.jslsolucoes.nginx.admin.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.LogRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class LogRepositoryImpl implements LogRepository {

	private NginxRepository nginxRepository;

	
	public LogRepositoryImpl() {
		
	}
	
	@Inject
	public LogRepositoryImpl(NginxRepository nginxRepository) {
		this.nginxRepository = nginxRepository;
	}
	
	@Override
	public void collect() {
		Nginx nginx = nginxRepository.configuration();
		FileUtils
			.iterateFiles(nginx.log(),new String[]{".log"},true)
			.forEachRemaining(file -> {
				System.out.println(file.getName());
			});
	}
}
