package com.jslsolucoes.nginx.admin.agent.client;

import java.util.concurrent.ScheduledExecutorService;

import javax.enterprise.inject.Vetoed;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.access.log.NginxAccessLogBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.cli.NginxCommandLineInterfaceBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.configure.NginxConfigureBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.error.log.NginxErrorLogBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.importation.NginxImportBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.info.NginxServerInfoBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.os.NginxOperationalSystemInfoBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.ping.NginxPingBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.ssl.NginxSslBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.status.NginxStatusBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.upstream.NginxUpstreamBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.virtual.host.NginxVirtualHostBuilder;

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
			return (T) NginxPingBuilder.newBuilder().withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxServerInfoBuilder.class)) {
			return (T) NginxServerInfoBuilder.newBuilder().withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxConfigureBuilder.class)) {
			return (T) NginxConfigureBuilder.newBuilder().withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxUpstreamBuilder.class)) {
			return (T) NginxUpstreamBuilder.newBuilder().withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxStatusBuilder.class)) {
			return (T) NginxStatusBuilder.newBuilder().withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxAccessLogBuilder.class)) {
			return (T) NginxAccessLogBuilder.newBuilder().withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxErrorLogBuilder.class)) {
			return (T) NginxErrorLogBuilder.newBuilder().withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxVirtualHostBuilder.class)) {
			return (T) NginxVirtualHostBuilder.newBuilder().withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxSslBuilder.class)) {
			return (T) NginxSslBuilder.newBuilder().withScheduledExecutorService(scheduledExecutorService);
		} else if (clazz.equals(NginxImportBuilder.class)) {
			return (T) NginxImportBuilder.newBuilder().withScheduledExecutorService(scheduledExecutorService);
		}
		throw new IllegalArgumentException("Please select an valid api");
	}

	public void close() {
		scheduledExecutorService.shutdown();
	}
}
