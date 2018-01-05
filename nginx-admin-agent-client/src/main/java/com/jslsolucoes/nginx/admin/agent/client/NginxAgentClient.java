package com.jslsolucoes.nginx.admin.agent.client;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.manager.NginxManagerBuilder;

public class NginxAgentClient {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String bin;

	public NginxAgentClient(ScheduledExecutorService scheduledExecutorService,String endpoint,String bin) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.bin = bin;
	}

	@SuppressWarnings("unchecked")
	public <T extends NginxAgentClientApiBuilder> T api(Class<T> clazz) {
		if (clazz.equals(NginxManagerBuilder.class)) {
			return (T) NginxManagerBuilder.newBuilder()
					.withEndpoint(endpoint)
					.withBin(bin)
					.withScheduledExecutorService(scheduledExecutorService);
		}
		throw new IllegalArgumentException("Please select an valid api");
	}

	public void close() {
		scheduledExecutorService.shutdown();
	}
}
