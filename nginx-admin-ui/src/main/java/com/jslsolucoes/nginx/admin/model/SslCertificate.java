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
package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ssl_certificate", schema = "admin")
public class SslCertificate implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "common_name")
	private String commonName;

	@Column(name = "certificate")
	private String certificate;

	@Column(name = "certificate_private_key")
	private String certificatePrivateKey;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="sslCertificate")
	private Set<VirtualHost> virtualHosts;

	public SslCertificate() {

	}

	public SslCertificate(Long id, String commonName, String certificate, String certificatePrivateKey) {
		this.id = id;
		this.commonName = commonName;
		this.certificate = certificate;
		this.certificatePrivateKey = certificatePrivateKey;
	}

	public SslCertificate(Long id) {
		this.id = id;
	}

	public SslCertificate(String commonName) {
		this.commonName = commonName;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCertificatePrivateKey() {
		return certificatePrivateKey;
	}

	public void setCertificatePrivateKey(String certificatePrivateKey) {
		this.certificatePrivateKey = certificatePrivateKey;
	}

	public Set<VirtualHost> getVirtualHosts() {
		return virtualHosts;
	}

}
