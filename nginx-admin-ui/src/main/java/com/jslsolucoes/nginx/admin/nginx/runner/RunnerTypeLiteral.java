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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((operationalSystemType == null) ? 0 : operationalSystemType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RunnerTypeLiteral other = (RunnerTypeLiteral) obj;
		if (operationalSystemType != other.operationalSystemType)
			return false;
		return true;
	}
}
