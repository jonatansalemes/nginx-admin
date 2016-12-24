package com.jslsolucoes.nginx.admin.nginx.status;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

public class NginxStatusReader {

	public NginxStatus read() {
		String body = body();
		if(!StringUtils.isEmpty(body)){
			NginxStatus nginxStatus = new NginxStatus();
			nginxStatus.setReading(reading(body));
			nginxStatus.setWriting(writing(body));
			nginxStatus.setActiveConnection(activeConnection(body));
			nginxStatus.setAccepts(accepts(body));
			nginxStatus.setWaiting(waiting(body));
			nginxStatus.setAccepts(accepts(body));
			nginxStatus.setHandled(handled(body));
			nginxStatus.setRequests(requests(body));
			return nginxStatus;
		}
		return null;
	}
	
	private Integer accepts(String body){
		Matcher accepts = Pattern.compile("([0-9]{1,})\\s([0-9]{1,})\\s([0-9]{1,})")
				.matcher(body);
		if (accepts.find()) {
			return Integer.valueOf(accepts.group(1));
		}
		return null;	
	}
	
	private Integer handled(String body){
		Matcher handled = Pattern.compile("([0-9]{1,})\\s([0-9]{1,})\\s([0-9]{1,})")
				.matcher(body);
		if (handled.find()) {
			return Integer.valueOf(handled.group(2));
		}
		return null;	
	}
	
	private Integer requests(String body){
		Matcher requests = Pattern.compile("([0-9]{1,})\\s([0-9]{1,})\\s([0-9]{1,})")
				.matcher(body);
		if (requests.find()) {
			return Integer.valueOf(requests.group(3));
		}
		return null;	
	}
	
	private Integer activeConnection(String body){
		Matcher activeConnection = Pattern.compile("Active connections:\\s([0-9]{1,})")
		.matcher(body);
		if (activeConnection.find()) {
			return Integer.valueOf(activeConnection.group(1));
		}
		return null;
	}
	
	private Integer reading(String body){
		Matcher reading = Pattern.compile("Reading:\\s([0-9]{1,})")
				.matcher(body);
		if (reading.find()) {
			return Integer.valueOf(reading.group(1));
		}
		return null;
	}
	
	private Integer writing(String body){
		Matcher writing = Pattern.compile("Writing:\\s([0-9]{1,})")
				.matcher(body);
		if (writing.find()) {
			return Integer.valueOf(writing.group(1));
		}
		return null;
	}
	
	private Integer waiting(String body){
		Matcher waiting = Pattern.compile("Waiting:\\s([0-9]{1,})")
				.matcher(body);
		if (waiting.find()) {
			return Integer.valueOf(waiting.group(1));
		}
		return null;
	}

	private String body() {
		try {
			CloseableHttpClient httpclient = client();
			HttpGet httpGet = new HttpGet("http://localhost/status");
			CloseableHttpResponse response = httpclient.execute(httpGet);
			String body = null;
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				body = EntityUtils.toString(response.getEntity());
			}
			response.close();
			httpclient.close();
			return body;
		} catch (Exception e) {
			return null;
		}
	}

	private CloseableHttpClient client() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(10000).build();
		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
		return HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).setSSLSocketFactory(sslsf).build();
	}
}
