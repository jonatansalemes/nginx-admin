package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.nio.file.Paths;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.agent.config.Configuration;
import com.jslsolucoes.runtime.RuntimeBuilder;
import com.jslsolucoes.runtime.RuntimeResult;
import com.jslsolucoes.runtime.RuntimeResultType;

@RequestScoped
public class NginxCommandLineInterfaceResourceImpl {

	private Configuration configuration;

	@Deprecated
	public NginxCommandLineInterfaceResourceImpl() {

	}

	@Inject
	public NginxCommandLineInterfaceResourceImpl(Configuration configuration) {
		this.configuration = configuration;
	}

	public RuntimeResult killAll() {
		return RuntimeBuilder.newBuilder().withCommand("sudo killall nginx").execute();
	}

	public RuntimeResult start() {
		return RuntimeBuilder.newBuilder().withCommand("sudo " + bin() + " -c " + conf()).execute();
	}

	public RuntimeResult stop() {
		return RuntimeBuilder.newBuilder().withCommand("sudo " + bin() + " -c " + conf() + " -s quit").execute();
	}

	public RuntimeResult version() {
		return RuntimeBuilder.newBuilder().withCommand("sudo " + bin() + " -v ").execute();
	}

	public RuntimeResult reload() {
		return RuntimeBuilder.newBuilder().withCommand("sudo " + bin() + " -c " + conf() + " -s reload").execute();
	}

	public RuntimeResult testConfiguration() {
		return RuntimeBuilder.newBuilder().withCommand("sudo " + bin() + " -c " + conf() + " -t").execute();
	}

	public RuntimeResult status() {
		return RuntimeBuilder.newBuilder().withCommand("sudo pgrep nginx").execute();
	}

	private String conf() {
		return Paths.get(configuration.getNginx().getSetting(), "nginx.conf").toAbsolutePath().toString();
	}

	private String bin() {
		return configuration.getNginx().getBin();
	}

	public RuntimeResult restart() {
		RuntimeResult runtimeResultForStop = stop();
		RuntimeResult runtimeResultForStart = start();
		return new RuntimeResult(
				runtimeResultForStop.isSuccess() && runtimeResultForStart.isSuccess() ? RuntimeResultType.SUCCESS
						: RuntimeResultType.ERROR,
				runtimeResultForStop.getOutput().concat("\n").concat(runtimeResultForStart.getOutput()));
	}

}
