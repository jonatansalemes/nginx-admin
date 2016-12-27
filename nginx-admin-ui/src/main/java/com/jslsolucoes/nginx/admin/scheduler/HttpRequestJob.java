package com.jslsolucoes.nginx.admin.scheduler;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(HttpRequestJob.class);

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			CloseableHttpClient httpclient = client();
			String host = jobExecutionContext.getMergedJobDataMap().getString("url_base") 
							+ "/task/collect/log";
			System.out.println(host);
			HttpGet httpGet = new HttpGet(host);
			CloseableHttpResponse response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				logger.error("Job execution error ");
			}
			response.close();
			httpclient.close();
		} catch (Exception e) {
			logger.error("Job cannot be done");
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
