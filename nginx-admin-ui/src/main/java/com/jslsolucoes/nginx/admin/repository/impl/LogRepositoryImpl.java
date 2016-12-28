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
