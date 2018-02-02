package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jslsolucoes.vraptor4.auth.model.AuthUser;

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
@SequenceGenerator(name = "user_sq", initialValue = 1, allocationSize = 1, sequenceName = "user_sq")
public class User implements Serializable, AuthUser {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sq")
	private Long id;

	@Column(name = "login")
	private String login;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "password_force_change")
	private Integer passwordForceChange;

	public User() {
		// default constructor
	}

	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public User(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public Integer getPasswordForceChange() {
		return passwordForceChange;
	}

	public void setPasswordForceChange(Integer passwordForceChange) {
		this.passwordForceChange = passwordForceChange;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
