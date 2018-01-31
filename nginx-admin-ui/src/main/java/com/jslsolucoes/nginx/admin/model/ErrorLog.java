package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "error_log", schema = "admin")
@SequenceGenerator(name = "error_log_sq", initialValue = 1, schema = "admin", allocationSize = 1, sequenceName = "admin.error_log_sq")
public class ErrorLog implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="error_log_sq")
	private Long id;

	@Column(name = "date_time")
	private Date timestamp;

	@Column(name = "level")
	private String level;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nginx")
	private Nginx nginx;

	@Column(name = "message")
	private String message;

	@Column(name = "pid")
	private Long pid;

	@Column(name = "cid")
	private Long cid;

	@Column(name = "tid")
	private Long tid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Nginx getNginx() {
		return nginx;
	}

	public void setNginx(Nginx nginx) {
		this.nginx = nginx;
	}
}
