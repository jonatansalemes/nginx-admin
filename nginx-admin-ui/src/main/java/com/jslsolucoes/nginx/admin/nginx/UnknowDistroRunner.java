package com.jslsolucoes.nginx.admin.nginx;

import javax.enterprise.inject.Vetoed;

import com.jslsolucoes.nginx.admin.os.OperationalSystemDistribution;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResult;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResultType;

@Vetoed
public class UnknowDistroRunner implements Runner {

	private static final RuntimeResult RUNTIME_RESULT = new RuntimeResult(RuntimeResultType.ERROR,
			"We dont know your distribution please report issue o github project");

	@Override
	public OperationalSystemDistribution distro() {
		return OperationalSystemDistribution.UNKNOW_DISTRIBUTION;
	}

	@Override
	public RuntimeResult start(NginxConfiguration nginxConfiguration) {
		return RUNTIME_RESULT;
	}

	@Override
	public RuntimeResult stop(NginxConfiguration nginxConfiguration) {
		return RUNTIME_RESULT;
	}

	@Override
	public RuntimeResult restart(NginxConfiguration nginxConfiguration) {
		return RUNTIME_RESULT;
	}

	@Override
	public RuntimeResult status(NginxConfiguration nginxConfiguration) {
		return RUNTIME_RESULT;
	}

}
