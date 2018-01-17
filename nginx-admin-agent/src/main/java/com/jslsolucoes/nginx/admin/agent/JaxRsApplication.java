package com.jslsolucoes.nginx.admin.agent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.jslsolucoes.nginx.admin.agent.resource.IndexResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxAccessLogResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxCommandLineInterfaceResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxErrorLogResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxFileSystemResource;

@ApplicationPath("/")
public class JaxRsApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(Arrays.asList(IndexResource.class, NginxCommandLineInterfaceResource.class,
				NginxFileSystemResource.class,NginxAccessLogResource.class,NginxErrorLogResource.class));
	}

}
