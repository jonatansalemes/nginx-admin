package com.jslsolucoes.nginx.admin.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

public class ExecutorFactory {

	@Produces
	@ApplicationScoped
	public ExecutorService getInstance() {
		return Executors.newCachedThreadPool();
	}

	public void destroy(@Disposes ExecutorService executorService) {
		if (!executorService.isShutdown()) {
			executorService.shutdown();
		}
	}

}