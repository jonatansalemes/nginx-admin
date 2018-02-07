package com.jslsolucoes.nginx.admin.agent.client.api.impl.importation;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;

public class NginxImportBuilder implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String authorizationKey;
	private String conf;

	private NginxImportBuilder() {

	}

	@Override
	public NginxImport build() {
		return new NginxImport(scheduledExecutorService, endpoint, authorizationKey, conf);
	}

	public static NginxImportBuilder newBuilder() {
		return new NginxImportBuilder();
	}

	public NginxImportBuilder withScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}

	public NginxImportBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxImportBuilder withConf(String conf) {
		this.conf = conf;
		return this;
	}

	public NginxImportBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
