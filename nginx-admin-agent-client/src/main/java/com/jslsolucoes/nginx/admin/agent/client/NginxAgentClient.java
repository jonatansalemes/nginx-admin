package com.jslsolucoes.nginx.admin.agent.client;

import java.util.concurrent.ScheduledExecutorService;

import javax.enterprise.inject.Vetoed;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxCommandLineInterfaceBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxConfigureBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxOperationalSystemInfoBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxPingBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxServerInfoBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxStatusBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxUpstreamBuilder;

@Vetoed
public class NginxAgentClient {

	private final ScheduledExecutorService scheduledExecutorService;

	public NginxAgentClient(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
	}

	@SuppressWarnings("unchecked")
	public <T extends NginxAgentClientApiBuilder> T api(Class<T> clazz) {
		if (clazz.equals(NginxCommandLineInterfaceBuilder.class)) {
			return (T) NginxCommandLineInterfaceBuilder.newBuilder()
					.withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxOperationalSystemInfoBuilder.class)) {
			return (T) NginxOperationalSystemInfoBuilder.newBuilder()
					.withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxPingBuilder.class)) {
			return (T) NginxPingBuilder.newBuilder()
					.withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxServerInfoBuilder.class)) {
			return (T) NginxServerInfoBuilder.newBuilder()
					.withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxConfigureBuilder.class)) {
			return (T) NginxConfigureBuilder.newBuilder()
					.withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxUpstreamBuilder.class)) {
			return (T) NginxUpstreamBuilder.newBuilder()
					.withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxStatusBuilder.class)) {
			return (T) NginxStatusBuilder.newBuilder()
					.withScheduledExecutorService(scheduledExecutorService);
		}
		throw new IllegalArgumentException("Please select an valid api");
	}

	public void close() {
		scheduledExecutorService.shutdown();
	}
}
