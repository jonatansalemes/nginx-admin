package com.jslsolucoes.nginx.admin.agent.client.api;

import com.jslsolucoes.nginx.admin.agent.client.api.impl.access.log.NginxAccessLogBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.cli.NginxCommandLineInterfaceBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.configure.NginxConfigureBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.error.log.NginxErrorLogBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.info.NginxServerInfoBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.os.NginxOperationalSystemInfoBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.ping.NginxPingBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.ssl.NginxSslBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.status.NginxStatusBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.upstream.NginxUpstreamBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.virtual.host.NginxVirtualHostBuilder;

public class NginxAgentClientApis {

	public static Class<NginxCommandLineInterfaceBuilder> cli() {
		return NginxCommandLineInterfaceBuilder.class;
	}

	public static Class<NginxOperationalSystemInfoBuilder> os() {
		return NginxOperationalSystemInfoBuilder.class;
	}
	
	public static Class<NginxPingBuilder> ping() {
		return NginxPingBuilder.class;
	}
	
	public static Class<NginxServerInfoBuilder> info() {
		return NginxServerInfoBuilder.class;
	}
	
	public static Class<NginxStatusBuilder> status() {
		return NginxStatusBuilder.class;
	}
	
	public static Class<NginxConfigureBuilder> configure() {
		return NginxConfigureBuilder.class;
	}
	
	public static Class<NginxUpstreamBuilder> upstream() {
		return NginxUpstreamBuilder.class;
	}
	
	public static Class<NginxAccessLogBuilder> accessLog() {
		return NginxAccessLogBuilder.class;
	}
	
	public static Class<NginxErrorLogBuilder> errorLog() {
		return NginxErrorLogBuilder.class;
	}
	
	public static Class<NginxVirtualHostBuilder> virtualHost() {
		return NginxVirtualHostBuilder.class;
	}
	
	public static Class<NginxSslBuilder> ssl() {
		return NginxSslBuilder.class;
	}

}
