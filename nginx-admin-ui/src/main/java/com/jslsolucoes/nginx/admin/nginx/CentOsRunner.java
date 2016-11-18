package com.jslsolucoes.nginx.admin.nginx;

import javax.enterprise.inject.Vetoed;

import com.jslsolucoes.nginx.admin.os.OperationalSystemDistribution;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResult;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResultType;
import com.jslsolucoes.nginx.admin.runtime.RuntimeUtils;

@Vetoed
public class CentOsRunner implements Runner {

	@Override
	public OperationalSystemDistribution distro() {
		return OperationalSystemDistribution.CENTOS;
	}

	@Override
	public RuntimeResult start(NginxConfiguration nginxConfiguration) {
		return RuntimeUtils.command(nginxConfiguration.getBin().getAbsolutePath() + " -c "
				+ nginxConfiguration.getConf().getAbsolutePath());
	}

	@Override
	public RuntimeResult stop(NginxConfiguration nginxConfiguration) {
		return RuntimeUtils.command(nginxConfiguration.getBin().getAbsolutePath() + " -s quit");
	}

	@Override
	public RuntimeResult restart(NginxConfiguration nginxConfiguration) {
		stop(nginxConfiguration);
		start(nginxConfiguration);
		return status(nginxConfiguration);
	}

	@Override
	public RuntimeResult status(NginxConfiguration nginxConfiguration) {
		return new RuntimeResult(RuntimeResultType.SUCCESS,"Process checking ...");
	}

}
