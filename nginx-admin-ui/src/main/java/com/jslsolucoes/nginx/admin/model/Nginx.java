package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="nginx",schema="admin")
public class Nginx implements Serializable{
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(name="username")
	private String userName;

	@Column(name="groupname")
	private String groupName;
	
	@Column(name="home")
	private String home;
	
	@Column(name="bin")
	private String bin;
	
	@Column(name="conf")
	private String conf;
	
	@Column(name="error")
	private String error;
	
	@Column(name="access")
	private String access;
	
	@Column(name="pid")
	private String pid;
	
	@Column(name="lock")
	private String lock;
	
	public Nginx() {
		
	}
	
	public Nginx(Long id,String userName,String groupName,String home,String bin,String conf,String error,String access,
			String pid,String lock) {
		this.id = id;
		this.userName = userName;
		this.groupName = groupName;
		this.home = home;
		this.bin = bin;
		this.conf = conf;
		this.error = error;
		this.access = access;
		this.pid = pid;
		this.lock = lock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getConf() {
		return conf;
	}

	public void setConf(String conf) {
		this.conf = conf;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}
	
}
