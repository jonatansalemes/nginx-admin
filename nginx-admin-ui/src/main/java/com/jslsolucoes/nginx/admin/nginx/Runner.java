package com.jslsolucoes.nginx.admin.nginx;

import com.jslsolucoes.nginx.admin.os.OperationalSystemDistribution;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResult;

public interface Runner {
	public OperationalSystemDistribution distro();
	public RuntimeResult start(NginxConfiguration nginxConfiguration);
	public RuntimeResult stop(NginxConfiguration nginxConfiguration);
	public RuntimeResult restart(NginxConfiguration nginxConfiguration);
	public RuntimeResult status(NginxConfiguration nginxConfiguration);
}
