package com.jslsolucoes.nginx.admin.repository.impl;

public class OperationResult {

	private OperationStatusType operationType;
	private Long id;

	public OperationResult(OperationStatusType operationType, Long id) {
		this.operationType = operationType;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public OperationStatusType getOperationType() {
		return operationType;
	}
}