package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "strategy", schema = "admin")
@SequenceGenerator(name = "strategy_sq", initialValue = 1, schema = "admin", allocationSize = 1, sequenceName = "admin.strategy_sq")
public class Strategy implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="strategy_sq")
	private Long id;

	@Column(name = "name")
	private String name;

	public Strategy() {
		// default constructor
	}

	public Strategy(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
