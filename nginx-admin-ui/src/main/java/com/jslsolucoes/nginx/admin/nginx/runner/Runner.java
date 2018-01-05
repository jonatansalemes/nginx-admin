package com.jslsolucoes.nginx.admin.nginx.runner;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.runtime.RuntimeResult;

public interface Runner {
	public Runner configure(Nginx nginx);

	public RuntimeResult start();

	public RuntimeResult version();

	public RuntimeResult stop();

	public RuntimeResult restart();

	public RuntimeResult status();

	public RuntimeResult reload();

	public RuntimeResult testConfig();
}
