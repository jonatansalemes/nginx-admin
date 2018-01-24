package com.jslsolucoes.nginx.admin.agent.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxPingResponse;

public class NginxPingTest {

	private NginxAgentClient nginxAgentClient;

	@Before
	public void setUp() {
		nginxAgentClient = NginxAgentClientBuilder.newBuilder().build();
	}

	@Test
	public void ping() {
		nginxAgentClient.api(NginxAgentClientApis.ping())
				.withAuthorizationKey("fdoinsafodsoianoifd")
				.withEndpoint("https://localhost:3443")
				.build()
				.ping()
				.thenAccept(nginxResponse -> {
					if(nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if(nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxPingResponse nginxPingResponse = (NginxPingResponse) nginxResponse;
						Assert.assertEquals("pong",nginxPingResponse.getMessage());
					}
				}).join();
	}

	@After
	public void tearDown() {
		nginxAgentClient.close();
	}
}
