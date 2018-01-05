package com.jslsolucoes.nginx.admin.agent.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class NginxAgentClientBuilder {

	private final ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String bin;
	
	private NginxAgentClientBuilder() {
		this.scheduledExecutorService = Executors.newScheduledThreadPool(1,new ThreadFactory() {
			@Override
			public Thread newThread(Runnable runnable) {
				return new Thread(runnable,"watson-sdk");
			}
		});
	}
	
	public static NginxAgentClientBuilder newBuilder() {
		return new NginxAgentClientBuilder();
	}

	public NginxAgentClient build() {
		return new NginxAgentClient(scheduledExecutorService,endpoint,bin);
	}

	public NginxAgentClientBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}
	
	public NginxAgentClientBuilder withBin(String bin) {
		this.bin = bin;
		return this;
	}

}
