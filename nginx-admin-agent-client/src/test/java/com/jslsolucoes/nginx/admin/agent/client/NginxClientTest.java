package com.jslsolucoes.nginx.admin.agent.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jslsolucoes.nginx.admin.agent.client.api.manager.NginxManagerBuilder;

public class NginxClientTest {

	
	private NginxAgentClient nginxAgentClient;

	@Before
	public void setUp() {
		nginxAgentClient = NginxAgentClientBuilder.newBuilder().build();
	}

	@Test
	public void start() {
		nginxAgentClient.api(NginxManagerBuilder.class).build().start();
	}
	
	@After
	public void tearDown() {
		nginxAgentClient.close();
	}
}
