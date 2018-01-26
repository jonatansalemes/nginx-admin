package com.jslsolucoes.nginx.admin.agent;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClient;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.cli.NginxCommandLineInterface;
import com.jslsolucoes.nginx.admin.agent.model.Endpoint;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.Location;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class NginxAgentRunner {

	private NginxAgentClient nginxAgentClient;
	private NginxRepository nginxRepository;

	@Deprecated
	public NginxAgentRunner() {

	}

	@Inject
	public NginxAgentRunner(NginxAgentClient nginxAgentClient, NginxRepository nginxRepository) {
		this.nginxAgentClient = nginxAgentClient;
		this.nginxRepository = nginxRepository;
	}

	public NginxResponse stop(Long idNginx) {
		return nginxCommandLineInterface(idNginx).stop().join();
	}

	public NginxResponse createUpstream(Long idNginx, String uuid, String name, String strategy,
			List<Endpoint> endpoints) {
		return nginxAgentClient.api(NginxAgentClientApis.upstream()).withUuid(uuid)
				.withAuthorizationKey(authorizationKey(idNginx)).withEndpoint(endpoint(idNginx)).withName(name)
				.withStrategy(strategy).withEndpoints(endpoints).build().create().join();
	}

	public NginxResponse updateUpstream(Long idNginx, String uuid, String name, String strategy,
			List<Endpoint> endpoints) {
		return nginxAgentClient.api(NginxAgentClientApis.upstream()).withUuid(uuid)
				.withAuthorizationKey(authorizationKey(idNginx)).withEndpoint(endpoint(idNginx)).withName(name)
				.withStrategy(strategy).withEndpoints(endpoints).build().update().join();
	}

	public NginxResponse deleteUpstream(Long idNginx, String uuid) {
		return nginxAgentClient.api(NginxAgentClientApis.upstream()).withUuid(uuid)
				.withAuthorizationKey(authorizationKey(idNginx)).withEndpoint(endpoint(idNginx)).build().delete()
				.join();
	}

	public NginxResponse configure(Long idNginx, Boolean gzip, Integer maxPostSize) {
		return nginxAgentClient.api(NginxAgentClientApis.configure()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).withGzip(gzip).withMaxPostSize(maxPostSize).build().configure().join();
	}

	public NginxResponse statusForNginx(Long idNginx) {
		return nginxAgentClient.api(NginxAgentClientApis.status()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).build().status().join();
	}

	public NginxResponse info(Long idNginx) {
		return nginxAgentClient.api(NginxAgentClientApis.info()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).build().info().join();
	}

	public NginxResponse os(Long idNginx) {
		return nginxAgentClient.api(NginxAgentClientApis.os()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).build().operationalSystemInfo().join();
	}

	public NginxResponse reload(Long idNginx) {
		return nginxCommandLineInterface(idNginx).reload().join();
	}

	public NginxResponse start(Long idNginx) {
		return nginxCommandLineInterface(idNginx).start().join();
	}

	public NginxResponse status(Long idNginx) {
		return nginxCommandLineInterface(idNginx).status().join();
	}

	public NginxResponse restart(Long idNginx) {
		return nginxCommandLineInterface(idNginx).restart().join();
	}

	public NginxResponse testConfiguration(Long idNginx) {
		return nginxCommandLineInterface(idNginx).testConfiguration().join();
	}

	public NginxResponse killAll(Long idNginx) {
		return nginxCommandLineInterface(idNginx).killAll().join();
	}

	private NginxCommandLineInterface nginxCommandLineInterface(Long idNginx) {
		return nginxAgentClient.api(NginxAgentClientApis.cli()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).build();
	}

	public NginxResponse readUpstream(Long idNginx, String uuid) {
		return nginxAgentClient.api(NginxAgentClientApis.upstream()).withUuid(uuid)
				.withAuthorizationKey(authorizationKey(idNginx)).withEndpoint(endpoint(idNginx)).build().read().join();
	}

	public NginxResponse rotateAccessLog(Long idNginx) {
		return nginxAgentClient.api(NginxAgentClientApis.accessLog()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).build().rotate().join();
	}

	public NginxResponse collectAccessLog(Long idNginx) {
		return nginxAgentClient.api(NginxAgentClientApis.accessLog()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).build().collect().join();
	}

	public NginxResponse rotateErrorLog(Long idNginx) {
		return nginxAgentClient.api(NginxAgentClientApis.errorLog()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).build().rotate().join();
	}

	public NginxResponse collectErrorLog(Long idNginx) {
		return nginxAgentClient.api(NginxAgentClientApis.errorLog()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).build().collect().join();
	}

	public NginxResponse readVirtualHost(Long idNginx, String uuid) {
		return nginxAgentClient.api(NginxAgentClientApis.virtualHost()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).withUuid(uuid).build().read().join();
	}

	public NginxResponse deleteVirtualHost(Long idNginx, String uuid) {
		return nginxAgentClient.api(NginxAgentClientApis.virtualHost()).withUuid(uuid)
				.withAuthorizationKey(authorizationKey(idNginx)).withEndpoint(endpoint(idNginx)).build().delete()
				.join();
	}

	public NginxResponse createVirtualHost(Long idNginx, String uuid, List<String> aliases, String certificateUuid,
			Boolean https, String certificatePrivateKeyUuid, List<Location> locations) {
		return nginxAgentClient.api(NginxAgentClientApis.virtualHost()).withUuid(uuid)
				.withAuthorizationKey(authorizationKey(idNginx)).withEndpoint(endpoint(idNginx)).withAliases(aliases)
				.withHttps(https).withCertificateUuid(certificateUuid)
				.withCertificatePrivateKeyUuid(certificatePrivateKeyUuid).withLocations(locations).build().create()
				.join();
	}

	public NginxResponse updateVirtualHost(Long idNginx, String uuid, List<String> aliases, String certificateUuid,
			Boolean https, String certificatePrivateKeyUuid, List<Location> locations) {
		return nginxAgentClient.api(NginxAgentClientApis.virtualHost()).withUuid(uuid)
				.withAuthorizationKey(authorizationKey(idNginx)).withEndpoint(endpoint(idNginx)).withAliases(aliases)
				.withHttps(https).withCertificateUuid(certificateUuid)
				.withCertificatePrivateKeyUuid(certificatePrivateKeyUuid).withLocations(locations).build().create()
				.join();
	}

	public NginxResponse createSsl(Long idNginx, String uuid, FileObject fileObject) {
		return nginxAgentClient.api(NginxAgentClientApis.ssl()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).withUuid(uuid).withFileObject(fileObject).build().create().join();
	}

	public NginxResponse updateSsl(Long idNginx, String uuid, FileObject fileObject) {
		return nginxAgentClient.api(NginxAgentClientApis.ssl()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).withUuid(uuid).withFileObject(fileObject).build().update().join();
	}

	public NginxResponse readSsl(Long idNginx, String uuid) {
		return nginxAgentClient.api(NginxAgentClientApis.ssl()).withAuthorizationKey(authorizationKey(idNginx))
				.withEndpoint(endpoint(idNginx)).withUuid(uuid).build().read().join();
	}

	public NginxResponse deleteSsl(Long idNginx, String uuid) {
		return nginxAgentClient.api(NginxAgentClientApis.ssl()).withUuid(uuid)
				.withAuthorizationKey(authorizationKey(idNginx)).withEndpoint(endpoint(idNginx)).build().delete()
				.join();
	}
	
	public NginxResponse importFromNginxConfiguration(Long idNginx, String conf) {
		return nginxAgentClient.api(NginxAgentClientApis.importation()).withConf(conf)
				.withAuthorizationKey(authorizationKey(idNginx)).withEndpoint(endpoint(idNginx)).build().conf()
				.join();
	}

	private Nginx nginx(Long idNginx) {
		return nginxRepository.load(new Nginx(idNginx));
	}

	private String authorizationKey(Long idNginx) {
		return nginx(idNginx).getAuthorizationKey();
	}

	private String endpoint(Long idNginx) {
		return nginx(idNginx).getEndpoint();
	}
}
