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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class NginxRepositoryImpl extends RepositoryImpl<Nginx> implements NginxRepository {

	public NginxRepositoryImpl() {

	}

	@Inject
	public NginxRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Nginx nginx() {
		try {
			Query query = entityManager.createQuery("from Nginx");
			return (Nginx) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<String> validateBeforeUpdate(Nginx nginx) {
		List<String> errors = new ArrayList<String>();

		if (!new File(nginx.getBin()).exists()) {
			errors.add(Messages.getString("nginx.invalid.bin.file", nginx.getBin()));
		}

		if (!new File(nginx.getHome()).exists()) {
			errors.add(Messages.getString("nginx.invalid.home.folder", nginx.getBin()));
		}
		
		return errors;
	}
	
	@Override
	public Nginx update(Nginx nginx) {
		try {
			configure(nginx);
		} catch(IOException ioException){
			throw new RuntimeException(ioException);
		}
		return super.update(nginx);
	}
	
	private void configure(Nginx nginx) throws IOException {
		File home = new File(nginx.getHome(),"settings");
		FileUtils.forceMkdir(home);
		conf(home);
		mimeType(home);
		FileUtils.forceMkdir(new File(home,"virtual-domain"));
		FileUtils.forceMkdir(new File(home,"upstream"));
		FileUtils.forceMkdir(new File(home,"log"));
		FileUtils.forceMkdir(new File(home,"process"));
	}

	private void mimeType(File home) throws IOException {
		String template = IOUtils.toString(getClass().getResourceAsStream("/template/nginx/mime.types"),"UTF-8");
		FileUtils.writeStringToFile(new File(home,"mime.types"),template,"UTF-8");
	}

	private void conf(File home) throws IOException {
		String template = IOUtils.toString(getClass().getResourceAsStream("/template/nginx/nginx.conf"),"UTF-8")
				.replaceAll("&base&", home.getAbsolutePath());
		FileUtils.writeStringToFile(new File(home,"nginx.conf"),template,"UTF-8");
	}
}
