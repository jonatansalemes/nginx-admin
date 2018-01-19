package com.jslsolucoes.nginx.admin.agent.client;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxCommandLineInterfaceBuilder;

public class NginxAgentClient {

	private final ScheduledExecutorService scheduledExecutorService;
	private final String endpoint;
	private final String authorization;
	private String bin;
	private String home;

	public NginxAgentClient(ScheduledExecutorService scheduledExecutorService,String endpoint,String authorization,String bin,String home) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.endpoint = endpoint;
		this.authorization = authorization;
		this.bin = bin;
		this.home = home;
	}

	@SuppressWarnings("unchecked")
	public <T extends NginxAgentClientApiBuilder> T api(Class<T> clazz) {
		if (clazz.equals(NginxCommandLineInterfaceBuilder.class)) {
			return (T) NginxCommandLineInterfaceBuilder
						.newBuilder()
							.withBin(bin)
							.withHome(home)
							.withEndpoint(endpoint)
							.withAuthorization(authorization)
							.withScheduledExecutorService(scheduledExecutorService);
		}
		throw new IllegalArgumentException("Please select an valid api");
	}

	public void close() {
		scheduledExecutorService.shutdown();
	}
}
