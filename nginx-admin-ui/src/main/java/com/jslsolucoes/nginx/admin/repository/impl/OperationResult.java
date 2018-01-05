package com.jslsolucoes.nginx.admin.repository.impl;

public class OperationResult {

	private OperationType operationType;
	private Long id;

	public OperationResult(OperationType operationType, Long id) {
		this.operationType = operationType;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public OperationType getOperationType() {
		return operationType;
	}
}