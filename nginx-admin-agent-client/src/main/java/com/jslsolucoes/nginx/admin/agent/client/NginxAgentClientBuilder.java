package com.jslsolucoes.nginx.admin.agent.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class NginxAgentClientBuilder {

	private final ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String bin;
	private String authorization;
	private String home;
	
	private NginxAgentClientBuilder() {
		this.scheduledExecutorService = Executors.newScheduledThreadPool(1,new ThreadFactory() {
			@Override
			public Thread newThread(Runnable runnable) {
				return new Thread(runnable,"nginx-admin-agent-client");
			}
		});
	}
	
	public static NginxAgentClientBuilder newBuilder() {
		return new NginxAgentClientBuilder();
	}

	public NginxAgentClient build() {
		return new NginxAgentClient(scheduledExecutorService,endpoint,authorization,bin,home);
	}
	
	public NginxAgentClientBuilder withAuthorization(String authorization) {
		this.authorization = authorization;
		return this;
	}

	public NginxAgentClientBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxAgentClientBuilder withHome(String home) {
		this.home = home;
		return this;
	}

	public NginxAgentClientBuilder withBin(String bin) {
		this.bin = bin;
		return this;
	}
}
