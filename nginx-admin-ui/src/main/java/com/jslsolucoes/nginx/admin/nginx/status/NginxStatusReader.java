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
package com.jslsolucoes.nginx.admin.nginx.status;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.nginx.admin.http.HttpClientBuilder;

public class NginxStatusReader {

	protected String body;
	private static final String PATTERN = "([0-9]{1,})\\s([0-9]{1,})\\s([0-9]{1,})";
	private static Logger logger = LoggerFactory.getLogger(NginxStatusReader.class);

	public NginxStatusReader() {
		checkForStatus();
	}

	public NginxStatus status() {
		NginxStatus nginxStatus = new NginxStatus();
		nginxStatus.setReading(reading());
		nginxStatus.setWriting(writing());
		nginxStatus.setActiveConnection(activeConnection());
		nginxStatus.setAccepts(accepts());
		nginxStatus.setWaiting(waiting());
		nginxStatus.setAccepts(accepts());
		nginxStatus.setHandled(handled());
		nginxStatus.setRequests(requests());
		return nginxStatus;
	}

	private Integer accepts() {
		Matcher accepts = Pattern.compile(PATTERN).matcher(body);
		if (accepts.find()) {
			return Integer.valueOf(accepts.group(1));
		}
		return 0;
	}

	private Integer handled() {
		Matcher handled = Pattern.compile(PATTERN).matcher(body);
		if (handled.find()) {
			return Integer.valueOf(handled.group(2));
		}
		return 0;
	}

	private Integer requests() {
		Matcher requests = Pattern.compile(PATTERN).matcher(body);
		if (requests.find()) {
			return Integer.valueOf(requests.group(3));
		}
		return 0;
	}

	private Integer activeConnection() {
		Matcher activeConnection = Pattern.compile("Active connections:\\s([0-9]{1,})").matcher(body);
		if (activeConnection.find()) {
			return Integer.valueOf(activeConnection.group(1));
		}
		return 0;
	}

	private Integer reading() {
		Matcher reading = Pattern.compile("Reading:\\s([0-9]{1,})").matcher(body);
		if (reading.find()) {
			return Integer.valueOf(reading.group(1));
		}
		return 0;
	}

	private Integer writing() {
		Matcher writing = Pattern.compile("Writing:\\s([0-9]{1,})").matcher(body);
		if (writing.find()) {
			return Integer.valueOf(writing.group(1));
		}
		return 0;
	}

	private Integer waiting() {
		Matcher waiting = Pattern.compile("Waiting:\\s([0-9]{1,})").matcher(body);
		if (waiting.find()) {
			return Integer.valueOf(waiting.group(1));
		}
		return 0;
	}

	private void checkForStatus() {
		HttpClientBuilder.build().onError(exception -> empty()).client().get("http://localhost/status")
				.onStatus(HttpStatus.SC_OK, closeableHttpResponse -> {
					try {
						body(EntityUtils.toString(closeableHttpResponse.getEntity()));
					} catch (IOException iOException) {
						logger.error("Could not read status", iOException);
						empty();
					}
				}).onNotStatus(HttpStatus.SC_OK, closeableHttpResponse -> empty()).close();
	}

	private void empty() {
		this.body = "";
	}

	private void body(String body) {
		this.body = body;
	}
}
