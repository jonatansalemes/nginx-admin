package com.jslsolucoes.nginx.admin.agent.client.api.impl.virtual.host;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;
import com.jslsolucoes.nginx.admin.agent.model.Location;

public class NginxVirtualHostBuilder implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String authorizationKey;
	private String uuid;
	private Boolean https;
	private String certificateUuid;
	private String certificatePrivateKeyUuid;
	private List<String> aliases;
	private List<Location> locations;

	private NginxVirtualHostBuilder() {

	}

	@Override
	public NginxVirtualHost build() {
		return new NginxVirtualHost(scheduledExecutorService, endpoint, authorizationKey, uuid, https, certificateUuid, certificatePrivateKeyUuid, aliases, locations);
	}

	public static NginxVirtualHostBuilder newBuilder() {
		return new NginxVirtualHostBuilder();
	}

	public NginxVirtualHostBuilder withScheduledExecutorService(
			ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}
	
	public NginxVirtualHostBuilder withHttps(Boolean https) {
		this.https = https;
		return this;
	}
	
	public NginxVirtualHostBuilder withCertificateUuid(String certificateUuid) {
		this.certificateUuid = certificateUuid;
		return this;
	}
	
	public NginxVirtualHostBuilder withCertificatePrivateKeyUuid(String certificatePrivateKeyUuid) {
		this.certificatePrivateKeyUuid = certificatePrivateKeyUuid;
		return this;
	}
	
	public NginxVirtualHostBuilder withAliases(List<String> aliases) {
		this.aliases = aliases;
		return this;
	}
	
	public NginxVirtualHostBuilder withLocations(List<Location> locations) {
		this.locations = locations;
		return this;
	}
	
	public NginxVirtualHostBuilder withUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}

	public NginxVirtualHostBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxVirtualHostBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
