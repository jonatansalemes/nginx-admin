package com.jslsolucoes.nginx.admin.agent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.jslsolucoes.nginx.admin.agent.resource.NginxAccessLogResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxAdminResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxCommandLineInterfaceResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxErrorLogResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxImportResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxIndexResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxSslResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxUpstreamResource;
import com.jslsolucoes.nginx.admin.agent.resource.NginxVirtualHostResource;

@ApplicationPath("/")
public class JaxRsApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(Arrays.asList(NginxIndexResource.class, NginxCommandLineInterfaceResource.class,
				NginxAdminResource.class, NginxAccessLogResource.class, NginxErrorLogResource.class,
				NginxSslResource.class, NginxUpstreamResource.class, NginxVirtualHostResource.class,
				NginxImportResource.class));
	}

}
