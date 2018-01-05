package com.jslsolucoes.nginx.admin.nginx.runner.impl;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;
import com.jslsolucoes.nginx.admin.nginx.runner.RunnerType;
import com.jslsolucoes.nginx.admin.os.OperationalSystemType;
import com.jslsolucoes.runtime.RuntimeResult;
import com.jslsolucoes.runtime.RuntimeResultType;
import com.jslsolucoes.vaptor4.misc.i18n.Messages;

@RunnerType(OperationalSystemType.UNKNOW)
public class UnknowRunner implements Runner {

	private static final RuntimeResult RUNTIME_RESULT = new RuntimeResult(RuntimeResultType.ERROR,
			Messages.getString("machine.runner.not.found"));

	@Override
	public RuntimeResult start() {
		return RUNTIME_RESULT;
	}

	@Override
	public RuntimeResult stop() {
		return RUNTIME_RESULT;
	}

	@Override
	public RuntimeResult restart() {
		return RUNTIME_RESULT;
	}

	@Override
	public RuntimeResult status() {
		return RUNTIME_RESULT;
	}

	@Override
	public Runner configure(Nginx nginx) {
		return this;
	}

	@Override
	public RuntimeResult testConfig() {
		return RUNTIME_RESULT;
	}

	@Override
	public RuntimeResult version() {
		return RUNTIME_RESULT;
	}

	@Override
	public RuntimeResult reload() {
		return RUNTIME_RESULT;
	}
}
