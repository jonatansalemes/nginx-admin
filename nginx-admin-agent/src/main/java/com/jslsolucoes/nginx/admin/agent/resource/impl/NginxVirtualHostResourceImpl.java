package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.io.File;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.jslsolucoes.file.system.FileSystemBuilder;
import com.jslsolucoes.nginx.admin.agent.model.Location;
import com.jslsolucoes.template.TemplateProcessor;

@RequestScoped
public class NginxVirtualHostResourceImpl {
	
	
	public NginxVirtualHostResourceImpl() {

	}
	
	private File virtualHost(String nginxHome) {
		return new File(nginxHome,"virtual-host");
	}	

	public NginxOperationResult create(String nginxHome,String uuid,Boolean https,
			String certificateUuid,String certificatePrivateKeyUuid,
			List<String> aliases,List<Location> locations) {
		try {
			File virtualHostFolder = virtualHost(nginxHome);
			TemplateProcessor.newBuilder()
				.withTemplate("/template/nginx/dynamic","virtual-host.tpl")
					.withData("https",  https)
					.withData("certificateUuid",  certificateUuid)
					.withData("certificatePrivateKeyUuid",  certificatePrivateKeyUuid)
					.withData("nginxHome",  nginxHome)
					.withData("aliases",  aliases)
					.withData("locations",  locations)
					.withOutputLocation(new File(virtualHostFolder, uuid + ".conf"))
					.process();
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	public NginxOperationResult delete(String nginxHome,
			String uuid) {
		try {
			File virtualHostFolder = virtualHost(nginxHome);
			FileSystemBuilder
				.newBuilder()
				.delete()
					.withDestination(new File(virtualHostFolder,uuid + ".conf"))
					.execute()
				.end();
			
			return new NginxOperationResult(NginxOperationResultType.SUCCESS);
		} catch (Exception e) {
			return new NginxOperationResult(NginxOperationResultType.ERROR,ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	
}
