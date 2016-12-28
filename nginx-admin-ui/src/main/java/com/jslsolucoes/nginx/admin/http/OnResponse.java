package com.jslsolucoes.nginx.admin.http;

import org.apache.http.client.methods.CloseableHttpResponse;

public interface OnResponse {

	public void response(CloseableHttpResponse closeableHttpResponse) throws Exception;

}
