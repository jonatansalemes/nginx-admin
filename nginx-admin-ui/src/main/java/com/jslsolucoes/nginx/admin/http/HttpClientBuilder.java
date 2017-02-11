/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

public class HttpClientBuilder {

	private CloseableHttpClient closeableHttpClient;
	private CloseableHttpResponse closeableHttpResponse;
	private OnError onError;
	
	public static HttpClientBuilder build(){
		return new HttpClientBuilder();
	}
	
	public HttpClientBuilder onError(OnError onError){
		this.onError = onError;
		return this;
	}

	public HttpClientBuilder client() {
		try {
			RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(10000).build();
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
			closeableHttpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig)
					.setSSLSocketFactory(sslsf).build();
		} catch (Exception exception) {
			onError.error(exception);
		}
		return this;
	}

	public HttpClientBuilder get(String host) {
		try {
			HttpGet httpGet = new HttpGet(host);
			closeableHttpResponse = closeableHttpClient.execute(httpGet);
		} catch (Exception exception) {
			onError.error(exception);
		}
		return this;
	}

	public void close() {
		try {
			closeableHttpResponse.close();
			closeableHttpClient.close();
		} catch (Exception exception) {
			onError.error(exception);
		}
	}

	public HttpClientBuilder onNotStatus(Integer statusCode, OnResponse onResponse) {
		try {
			if (closeableHttpResponse.getStatusLine().getStatusCode() != statusCode) {
				onResponse.response(closeableHttpResponse);
			}
		} catch (Exception exception) {
			onError.error(exception);
		}
		return this;
	}

	public HttpClientBuilder onStatus(Integer statusCode, OnResponse onResponse) {
		try {
			if (closeableHttpResponse.getStatusLine().getStatusCode() == statusCode) {
				onResponse.response(closeableHttpResponse);
			}
		} catch (Exception exception) {
			onError.error(exception);
		}
		return this;
	}
}
