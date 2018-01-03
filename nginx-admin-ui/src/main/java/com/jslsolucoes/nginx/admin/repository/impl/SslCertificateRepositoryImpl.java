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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;
import com.jslsolucoes.nginx.admin.repository.SslCertificateRepository;

@RequestScoped
public class SslCertificateRepositoryImpl extends RepositoryImpl<SslCertificate> implements SslCertificateRepository {

	private NginxRepository nginxRepository;
	private ResourceIdentifierRepository resourceIdentifierRepository;

	public SslCertificateRepositoryImpl() {
		// Default constructor
	}

	@Inject
	public SslCertificateRepositoryImpl(EntityManager entityManager, NginxRepository nginxRepository,
			ResourceIdentifierRepository resourceIdentifierRepository) {
		super(entityManager);
		this.nginxRepository = nginxRepository;
		this.resourceIdentifierRepository = resourceIdentifierRepository;
	}

	@Override
	public OperationType deleteWithResource(SslCertificate sslCertificate) throws IOException {
		File ssl = nginxRepository.configuration().ssl();
		SslCertificate sslCertificateToDelete = load(sslCertificate);
		String sslCertificateHash = sslCertificateToDelete.getResourceIdentifierCertificate().getHash();
		String sslCertificatePrivateKeyHash = sslCertificateToDelete.getResourceIdentifierCertificatePrivateKey()
				.getHash();
		FileUtils.forceDelete(new File(ssl, sslCertificateHash));
		FileUtils.forceDelete(new File(ssl, sslCertificatePrivateKeyHash));
		super.delete(sslCertificateToDelete);
		resourceIdentifierRepository.delete(sslCertificateHash);
		resourceIdentifierRepository.delete(sslCertificatePrivateKeyHash);
		return OperationType.DELETE;
	}

	@Override
	public OperationResult saveOrUpdate(SslCertificate sslCertificate, InputStream certificateFile,
			InputStream certificatePrivateKeyFile) throws IOException {
		Nginx nginx = nginxRepository.configuration();
		if (certificateFile != null) {
			if (sslCertificate.getResourceIdentifierCertificate().getId() == null) {
				sslCertificate.setResourceIdentifierCertificate(resourceIdentifierRepository.create());
			}
			IOUtils.copy(certificateFile, new FileOutputStream(
					new File(nginx.ssl(), sslCertificate.getResourceIdentifierCertificate().getHash())));
		}
		if (certificatePrivateKeyFile != null) {
			if (sslCertificate.getResourceIdentifierCertificatePrivateKey().getId() == null) {
				sslCertificate.setResourceIdentifierCertificatePrivateKey(resourceIdentifierRepository.create());
			}
			IOUtils.copy(certificatePrivateKeyFile, new FileOutputStream(
					new File(nginx.ssl(), sslCertificate.getResourceIdentifierCertificatePrivateKey().getHash())));
		}
		return super.saveOrUpdate(sslCertificate);
	}

	@Override
	public InputStream download(String hash) throws FileNotFoundException {
		Nginx nginx = nginxRepository.configuration();
		return new FileInputStream(new File(nginx.ssl(), hash));
	}
}
