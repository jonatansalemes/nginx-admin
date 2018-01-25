package com.jslsolucoes.nginx.admin.agent.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxServerInfoResponse;

public class NginxServerInfoTest {

	private NginxAgentClient nginxAgentClient;

	@Before
	public void setUp() {
		nginxAgentClient = NginxAgentClientBuilder.newBuilder().build();
	}

	@Test
	public void info() {

		nginxAgentClient.api(NginxAgentClientApis.nginxServerInfo()).withAuthorizationKey("fdoinsafodsoianoifd")
				.withEndpoint("http://192.168.99.100:3000")
				.withHome("/opt/nginx-agent/settings")
				.withBin("/usr/sbin/nginx").build().info().thenAccept(nginxResponse -> {
					if (nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if (nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxServerInfoResponse nginxServerInfoResponse = (NginxServerInfoResponse) nginxResponse;
						Assert.assertEquals("172.17.0.2",nginxServerInfoResponse.getAddress());
						Assert.assertEquals("1.12.2", nginxServerInfoResponse.getVersion());
					}
				}).join();
	}

	@After
	public void tearDown() {
		nginxAgentClient.close();
	}
}
