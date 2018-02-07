package com.jslsolucoes.nginx.admin.agent.client;

import java.io.Closeable;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;

public class RestClient implements Closeable, Client {

	private Client client;

	public RestClient() throws KeyManagementException, NoSuchAlgorithmException {
		client = ClientBuilder.newBuilder().sslContext(sslContext()).hostnameVerifier(hostnameVerifier()).build();
	}

	private HostnameVerifier hostnameVerifier() {
		return (hostname, session) -> {
				return true;
		};
	}

	private SSLContext sslContext() {
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[]{};
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
					//do nothing
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
					//do nothing
				}
			} }, new SecureRandom());
			return sslContext;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static RestClient build() throws KeyManagementException, NoSuchAlgorithmException {
		return new RestClient();
	}

	@Override
	public Configuration getConfiguration() {
		return client.getConfiguration();
	}

	@Override
	public javax.ws.rs.client.Client property(String arg0, Object arg1) {
		return client.property(arg0, arg1);
	}

	@Override
	public javax.ws.rs.client.Client register(Class<?> arg0) {
		return client.register(arg0);
	}

	@Override
	public javax.ws.rs.client.Client register(Object arg0) {
		return client.register(arg0);
	}

	@Override
	public javax.ws.rs.client.Client register(Class<?> arg0, int arg1) {
		return client.register(arg0, arg1);
	}

	@Override
	public javax.ws.rs.client.Client register(Class<?> arg0, Class<?>... arg1) {
		return client.register(arg0, arg1);
	}

	@Override
	public javax.ws.rs.client.Client register(Class<?> arg0, Map<Class<?>, Integer> arg1) {
		return client.register(arg0, arg1);
	}

	@Override
	public javax.ws.rs.client.Client register(Object arg0, int arg1) {
		return client.register(arg0, arg1);
	}

	@Override
	public javax.ws.rs.client.Client register(Object arg0, Class<?>... arg1) {
		return client.register(arg0, arg1);
	}

	@Override
	public javax.ws.rs.client.Client register(Object arg0, Map<Class<?>, Integer> arg1) {
		return client.register(arg0, arg1);
	}

	@Override
	public HostnameVerifier getHostnameVerifier() {
		return client.getHostnameVerifier();
	}

	@Override
	public SSLContext getSslContext() {
		return client.getSslContext();
	}

	@Override
	public Builder invocation(Link arg0) {
		return client.invocation(arg0);
	}

	@Override
	public WebTarget target(String arg0) {
		return client.target(arg0);
	}

	@Override
	public WebTarget target(URI arg0) {
		return client.target(arg0);
	}

	@Override
	public WebTarget target(UriBuilder arg0) {
		return client.target(arg0);
	}

	@Override
	public WebTarget target(Link arg0) {
		return client.target(arg0);
	}

	@Override
	public void close() {
		client.close();
	}

}