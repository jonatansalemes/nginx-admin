package com.jslsolucoes.nginx.admin.nginx.runner;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.os.OperationalSystem;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

public class RunnerSelector {

	@Inject
	private NginxRepository nginxRepository;

	@ApplicationScoped
	@Produces
	public Runner getInstance(@Any Instance<Runner> runners) {
		Runner runner = runners.select(new RunnerTypeLiteral(OperationalSystem.info().getOperationalSystemType()))
				.get();
		runner.configure(nginxRepository.configuration());
		return runner;
	}
}
