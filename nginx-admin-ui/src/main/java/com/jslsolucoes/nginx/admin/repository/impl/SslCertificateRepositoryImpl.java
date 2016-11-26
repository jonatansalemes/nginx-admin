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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.io.IOUtils;

import com.itextpdf.text.pdf.security.CertificateInfo.X500Name;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.SslCertificateRepository;

@RequestScoped
public class SslCertificateRepositoryImpl extends RepositoryImpl<SslCertificate> implements SslCertificateRepository {

	private NginxRepository nginxRepository;

	public SslCertificateRepositoryImpl() {

	}

	@Inject
	public SslCertificateRepositoryImpl(EntityManager entityManager, NginxRepository nginxRepository) {
		super(entityManager);
		this.nginxRepository = nginxRepository;
	}

	@Override
	public void upload(InputStream certificate, InputStream key) throws Exception {
		Nginx nginx = nginxRepository.nginx();
		File settings = new File(nginx.getHome(), "settings");
		File ssl = new File(settings, "ssl");
		
		String cn = CertificateFactory.getInstance("X.509")
				.generateCertificates(certificate)
			.stream()
			.map(x509 -> {
				X509Certificate x509Certificate = (X509Certificate) x509;
				X500Name x500Name = new X500Name(x509Certificate.getSubjectX500Principal().getName());
				return x500Name.getField("CN");
		}).reduce("", String::concat);
		
		IOUtils.copy(certificate, new FileOutputStream(new File(ssl, cn + ".cer")));
		IOUtils.copy(key, new FileOutputStream(new File(ssl, cn + ".key")));
	}
}
