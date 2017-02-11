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
