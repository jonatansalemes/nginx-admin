package com.jslsolucoes.nginx.admin.agent.client;

import java.util.concurrent.ScheduledExecutorService;

import javax.enterprise.inject.Vetoed;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxCommandLineInterfaceBuilder;

@Vetoed
public class NginxAgentClient {

	private final ScheduledExecutorService scheduledExecutorService;
	
	public NginxAgentClient(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
	}

	@SuppressWarnings("unchecked")
	public <T extends NginxAgentClientApiBuilder> T api(Class<T> clazz) {
		if (clazz.equals(NginxCommandLineInterfaceBuilder.class)) {
			return (T) NginxCommandLineInterfaceBuilder
						.newBuilder()
							.withScheduledExecutorService(scheduledExecutorService);
		}
		throw new IllegalArgumentException("Please select an valid api");
	}

	public void close() {
		scheduledExecutorService.shutdown();
	}
}
