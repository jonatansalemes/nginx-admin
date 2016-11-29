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
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.util.TemplateProcessor;

@RequestScoped
public class NginxRepositoryImpl extends RepositoryImpl<Nginx> implements NginxRepository {

	public NginxRepositoryImpl() {

	}

	@Inject
	public NginxRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Nginx configuration() {
		return (Nginx) entityManager.createQuery("from Nginx").getSingleResult();
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Nginx nginx) {
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
	public OperationResult saveOrUpdate(Nginx nginx) {
		try {
			configure(nginx);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		return super.saveOrUpdate(nginx);
	}

	private void configure(Nginx nginx) throws Exception {
		File settings = new File(nginx.getHome(), "settings");
		if (!settings.exists()) {
			copy(settings);
		}
		new TemplateProcessor().withTemplate("nginx.tpl").withData("nginx", nginx)
				.toLocation(new File(settings, "nginx.conf")).process();
	}

	private void copy(File settings) throws IOException {
		FileUtils.forceMkdir(settings);
		copyToDirectory(getClass().getResource("/template/nginx"), settings, file -> {
			return !FilenameUtils.getExtension(file.getName()).equals("tpl");
		});
	}

	public void copyToDirectory(URL url, File destination, FileFilter fileFilter) throws IOException {
		URLConnection urlConnection = url.openConnection();
		if (urlConnection instanceof JarURLConnection) {
			copyFromJar(url, destination, fileFilter);
		} else {
			File source = new File(url.getPath());
			if (source.isDirectory()) {
				org.apache.commons.io.FileUtils.copyDirectory(source, destination, fileFilter);
			} else {
				org.apache.commons.io.FileUtils.copyFileToDirectory(source, destination);
			}
		}
	}

	private void copyFromJar(URL url, File destination, FileFilter fileFilter) throws IOException {
		JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		Enumeration<JarEntry> files = jarURLConnection.getJarFile().entries();
		while (files.hasMoreElements()) {
			JarEntry entry = files.nextElement();
			if (!entry.isDirectory()) {
				File file = new File(destination, entry.getName());
				if (fileFilter.accept(file)) {
					org.apache.commons.io.FileUtils
							.copyInputStreamToFile(jarURLConnection.getJarFile().getInputStream(entry), file);
				}
			} else {
				org.apache.commons.io.FileUtils.forceMkdir(new File(destination, entry.getName()));
			}
		}
	}
}
