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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "smtp", schema = "admin")
public class Smtp implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "host")
	private String host;

	@Column(name = "port")
	private Integer port;

	@Column(name = "tls")
	private Integer tls;

	@Column(name = "authenticate")
	private Integer authenticate;

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "from_address")
	private String fromAddress;

	public Smtp() {
		// default constructor
	}

	public Smtp(String host, Integer port, Integer authenticate, String userName, String password, Integer tls,
			String fromAddress) {
		this(null, host, port, authenticate, userName, password, tls, fromAddress);
	}

	public Smtp(Long id, String host, Integer port, Integer authenticate, String userName, String password, Integer tls,
			String fromAddress) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.authenticate = authenticate == null ? 0 : authenticate;
		this.tls = tls == null ? 0 : tls;
		this.userName = userName;
		this.password = password;
		this.fromAddress = fromAddress;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getTls() {
		return tls;
	}

	public void setTls(Integer tls) {
		this.tls = tls;
	}

	public Integer getAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(Integer authenticate) {
		this.authenticate = authenticate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
}
