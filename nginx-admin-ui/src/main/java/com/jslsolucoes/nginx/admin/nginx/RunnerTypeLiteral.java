package com.jslsolucoes.nginx.admin.nginx;

import javax.enterprise.util.AnnotationLiteral;

import com.jslsolucoes.nginx.admin.os.OperationalSystemDistribution;

@SuppressWarnings("serial")
public class RunnerTypeLiteral extends AnnotationLiteral<RunnerType> implements RunnerType {

	private OperationalSystemDistribution operationalSystemDistribution;

	public RunnerTypeLiteral(OperationalSystemDistribution operationalSystemDistribution) {
		this.operationalSystemDistribution = operationalSystemDistribution;
	}

	@Override
	public OperationalSystemDistribution value() {
		return operationalSystemDistribution;
	}

	
	
}
