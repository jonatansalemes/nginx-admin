package com.jslsolucoes.nginx.admin.nginx.runner;

import javax.enterprise.util.AnnotationLiteral;

import com.jslsolucoes.nginx.admin.os.OperationalSystemType;

@SuppressWarnings("serial")
public class RunnerTypeLiteral extends AnnotationLiteral<RunnerType> implements RunnerType {

	private OperationalSystemType operationalSystemType;

	public RunnerTypeLiteral(OperationalSystemType operationalSystemType) {
		this.operationalSystemType = operationalSystemType;
	}

	@Override
	public OperationalSystemType value() {
		return operationalSystemType;
	}

}
