package com.jslsolucoes.nginx.admin.agent.client;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxUpstream;
import com.jslsolucoes.nginx.admin.agent.model.Endpoint;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.upstream.NginxUpstreamCreateResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.upstream.NginxUpstreamDeleteResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.upstream.NginxUpstreamReadResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.upstream.NginxUpstreamUpdateResponse;

public class NginxUpstreamTest {

	private NginxAgentClient nginxAgentClient;
	private NginxUpstream nginxUpstream;

	@Before
	public void setUp() {
		nginxAgentClient = NginxAgentClientBuilder.newBuilder().build();
		nginxUpstream = nginxAgentClient.api(NginxAgentClientApis.upstream())
		.withUuid("fdsfsafdakdkdjs")
		.withAuthorizationKey("fdoinsafodsoianoifd")
		.withEndpoint("https://192.168.99.100:3443")
		.withName("upstream01")
		.withStrategy("round-robin")
		.withEndpoints(Arrays.asList(new Endpoint("192.168.0.1",88),new Endpoint("192.168.0.1",89)))
		.build();
	}

	@Test
	public void create() {
		nginxUpstream
		.delete()
		.thenCompose(nginxResponse -> nginxUpstream.create())
		.thenAccept(nginxResponse->{
					if (nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if (nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxUpstreamCreateResponse nginxUpstreamCreateResponse = (NginxUpstreamCreateResponse) nginxResponse;
						Assert.assertTrue(nginxUpstreamCreateResponse.getSuccess());
					}
				}).join();
	}
	
	@Test
	public void delete() {
		nginxUpstream
		.delete()
		.thenCompose(nginxResponse -> nginxUpstream.create())
		.thenCompose(nginxResponse -> nginxUpstream.delete())
		.thenAccept(nginxResponse->{
					if (nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if (nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxUpstreamDeleteResponse nginxUpstreamDeleteResponse = (NginxUpstreamDeleteResponse) nginxResponse;
						Assert.assertTrue(nginxUpstreamDeleteResponse.getSuccess());
					}
				}).join();
	}
	
	@Test
	public void update() {
		nginxUpstream
		.delete()
		.thenCompose(nginxResponse -> nginxUpstream.create())
		.thenCompose(nginxResponse -> nginxUpstream.update())
		.thenAccept(nginxResponse -> {
					if (nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if (nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxUpstreamUpdateResponse nginxUpstreamUpdateResponse = (NginxUpstreamUpdateResponse) nginxResponse;
						Assert.assertTrue(nginxUpstreamUpdateResponse.getSuccess());
					}
				}).join();
	}
	
	@Test
	public void read() {
		nginxUpstream
		.delete()
		.thenCompose(nginxResponse -> nginxUpstream.create())
		.thenCompose(nginxResponse -> nginxUpstream.read())
		.thenAccept(nginxResponse -> {
					if (nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if (nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxUpstreamReadResponse nginxUpstreamReadResponse = (NginxUpstreamReadResponse) nginxResponse;
						FileObject fileObject = nginxUpstreamReadResponse.getFileObject();
						Assert.assertNotNull(fileObject);
						Assert.assertEquals(Long.valueOf(85), fileObject.getSize());
						Assert.assertEquals("fdsfsafdakdkdjs.conf", fileObject.getFileName());
						Assert.assertEquals("upstream upstream01 {round-robin;server 192.168.0.1:88;server 192.168.0.1:89;}", fileObject.getContent().replaceAll("(\n|\t|\r)",""));
					}
				})
		.join();
	}

	@After
	public void tearDown() {
		nginxUpstream.delete().join();
		nginxAgentClient.close();
	}
}
