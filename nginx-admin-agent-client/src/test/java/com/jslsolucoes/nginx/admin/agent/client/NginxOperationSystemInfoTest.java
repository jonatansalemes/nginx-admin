package com.jslsolucoes.nginx.admin.agent.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxOperationalSystemDescriptionResponse;

public class NginxOperationSystemInfoTest {

	private NginxAgentClient nginxAgentClient;

	@Before
	public void setUp() {
		nginxAgentClient = NginxAgentClientBuilder.newBuilder().build();
	}

	@Test
	public void info() {
		nginxAgentClient.api(NginxAgentClientApis.operationalSystemInfo())
				.withAuthorizationKey("fdoinsafodsoianoifd")
				.withEndpoint("http://192.168.99.100:3000")
				.build()
				.operationalSystemInfo()
				.thenAccept(nginxResponse -> {
					if(nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if(nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxOperationalSystemDescriptionResponse nginxOperationalSystemDescriptionResponse = (NginxOperationalSystemDescriptionResponse) nginxResponse;
						Assert.assertEquals("amd64",nginxOperationalSystemDescriptionResponse.getArchitecture());
					}
				}).join();
	}

	@After
	public void tearDown() {
		nginxAgentClient.close();
	}
}
