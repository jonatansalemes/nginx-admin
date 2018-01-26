package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.io.File;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.nginx.admin.agent.config.Configuration;
import com.jslsolucoes.nginx.admin.agent.model.Location;
import com.jslsolucoes.template.TemplateProcessor;

@RequestScoped
public class NginxVirtualHostResourceImpl {
	
	
	private Configuration configuration;

	@Deprecated
	public NginxVirtualHostResourceImpl() {

	}
	
	@Inject
	public NginxVirtualHostResourceImpl(Configuration configuration) {
		this.configuration = configuration;
	}

	public NginxOperationResult create(String uuid,Boolean https,
			String certificateUuid,String certificatePrivateKeyUuid,
			List<String> aliases,List<Location> locations) {
		try {
			TemplateProcessor.newBuilder()
				.withTemplate("/template/nginx/dynamic","virtual-host.tpl")
					.withData("https",  https)
					.withData("certificateUuid",  certificateUuid)
					.withData("certificatePrivateKeyUuid",  certificatePrivateKeyUuid)
					.withData("settings",  settings())
					.withData("aliases",  aliases)
					.withData("locations",  locations)
					.withOutputLocation(virtualHost(uuid))
					.process();
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,e);
		}
	}
	
	public NginxOperationResult delete(String uuid) {
		try {
			FileSystemBuilder
				.newBuilder()
				.delete()
					.withDestination(virtualHost(uuid))
					.execute()
				.end();
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,e);
		}
	}
	
	
	private File virtualHost(String uuid) {
		return new File(virtualHostFolder(),uuid + ".conf");
	}
	
	private File virtualHostFolder() {
		return new File(settings(),"virtual-host");
	}	
	
	private String settings() {
		return configuration.getNginx().getSetting();
	}
	
}
