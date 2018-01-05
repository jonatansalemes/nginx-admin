package com.jslsolucoes.nginx.admin.nginx.runner.impl;

import org.apache.commons.io.FilenameUtils;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;
import com.jslsolucoes.nginx.admin.nginx.runner.RunnerType;
import com.jslsolucoes.nginx.admin.os.OperationalSystemType;
import com.jslsolucoes.runtime.RuntimeResult;
import com.jslsolucoes.runtime.RuntimeResultType;
import com.jslsolucoes.runtime.RuntimeUtils;
import com.jslsolucoes.vaptor4.misc.i18n.Messages;

@RunnerType(OperationalSystemType.LINUX)
public class LinuxRunner implements Runner {

	private Nginx nginx;
	private static final String SUDO = "sudo ";

	@Override
	public RuntimeResult start() {
		RuntimeUtils.command(SUDO + executable() + " -c " + nginx.conf().getAbsolutePath(), nginx.binFolder(), 3);
		return status();
	}

	@Override
	public RuntimeResult stop() {
		RuntimeUtils.command(SUDO + executable() + " -c " + nginx.conf().getAbsolutePath() + " -s quit",
				nginx.binFolder(), 3);
		return status();
	}

	@Override
	public RuntimeResult restart() {
		stop();
		start();
		return status();
	}

	@Override
	public RuntimeResult status() {
		RuntimeResult runtimeResult = RuntimeUtils.command(SUDO + " pgrep " + executable());
		if (runtimeResult.getRuntimeResultType().equals(RuntimeResultType.SUCCESS)) {
			return new RuntimeResult(RuntimeResultType.SUCCESS, Messages.getString("running"));
		} else {
			return new RuntimeResult(RuntimeResultType.SUCCESS, Messages.getString("stopped"));
		}
	}

	@Override
	public Runner configure(Nginx nginx) {
		this.nginx = nginx;
		return this;
	}

	@Override
	public RuntimeResult testConfig() {
		RuntimeResult runtimeResult = RuntimeUtils
				.command(SUDO + executable() + " -c " + nginx.conf().getAbsolutePath() + " -t", nginx.binFolder());
		if (runtimeResult.getRuntimeResultType().equals(RuntimeResultType.SUCCESS)) {
			if (runtimeResult.getOutput().contains("syntax is ok")) {
				return new RuntimeResult(RuntimeResultType.SUCCESS, Messages.getString("syntax.ok"));
			} else {
				return new RuntimeResult(RuntimeResultType.ERROR, runtimeResult.getOutput());
			}
		}
		return runtimeResult;
	}

	private String executable() {
		return FilenameUtils.getName(nginx.getBin());
	}

	@Override
	public RuntimeResult version() {
		return RuntimeUtils.command(SUDO + executable() + " -c " + nginx.conf().getAbsolutePath() + " -v",
				nginx.binFolder());
	}

	@Override
	public RuntimeResult reload() {
		return RuntimeUtils.command(SUDO + executable() + "  -c " + nginx.conf().getAbsolutePath() + " -s reload",
				nginx.binFolder());
	}

}
