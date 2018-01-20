package com.jslsolucoes.nginx.admin.agent.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class NginxAgentClientBuilder {

	private final ScheduledExecutorService scheduledExecutorService;
	
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
		return new NginxAgentClient(scheduledExecutorService);
	}
	
}
