package com.jslsolucoes.nginx.admin.agent.client.api.impl.ssl;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;

public class NginxSslBuilder implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String authorizationKey;
	private String uuid;
	private FileObject fileObject;

	private NginxSslBuilder() {

	}

	@Override
	public NginxSsl build() {
		return new NginxSsl(scheduledExecutorService, endpoint, authorizationKey, uuid, fileObject);
	}

	public static NginxSslBuilder newBuilder() {
		return new NginxSslBuilder();
	}

	public NginxSslBuilder withScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}

	public NginxSslBuilder withUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}

	public NginxSslBuilder withFileObject(FileObject fileObject) {
		this.fileObject = fileObject;
		return this;
	}

	public NginxSslBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxSslBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
