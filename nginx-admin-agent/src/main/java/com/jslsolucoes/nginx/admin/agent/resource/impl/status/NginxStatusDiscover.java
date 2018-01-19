package com.jslsolucoes.nginx.admin.agent.resource.impl.status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@RequestScoped
public class NginxStatusDiscover {

	private static final String PATTERN = "([0-9]{1,})\\s([0-9]{1,})\\s([0-9]{1,})";
	
	public NginxStatus status() {
		String response = response();
		NginxStatus nginxStatus = new NginxStatus();
		nginxStatus.setReading(reading(response));
		nginxStatus.setWriting(writing(response));
		nginxStatus.setActiveConnection(activeConnection(response));
		nginxStatus.setAccepts(accepts(response));
		nginxStatus.setWaiting(waiting(response));
		nginxStatus.setAccepts(accepts(response));
		nginxStatus.setHandled(handled(response));
		nginxStatus.setRequests(requests(response));
		return nginxStatus;
	}
	
	private String response() {
		Client client = ClientBuilder.newClient();
		try {
			Response response = client.target("http://localhost").path("/status").request().get();
			if(response.getStatusInfo().equals(Status.OK)){
				return response.readEntity(String.class);
			} else {
				return "";
			}
		} finally {
			client.close();
		}
	}

	private Integer accepts(String response) {
		Matcher accepts = Pattern.compile(PATTERN).matcher(response);
		if (accepts.find()) {
			return Integer.valueOf(accepts.group(1));
		}
		return 0;
	}

	private Integer handled(String response) {
		Matcher handled = Pattern.compile(PATTERN).matcher(response);
		if (handled.find()) {
			return Integer.valueOf(handled.group(2));
		}
		return 0;
	}

	private Integer requests(String response) {
		Matcher requests = Pattern.compile(PATTERN).matcher(response);
		if (requests.find()) {
			return Integer.valueOf(requests.group(3));
		}
		return 0;
	}

	private Integer activeConnection(String response) {
		Matcher activeConnection = Pattern.compile("Active connections:\\s([0-9]{1,})").matcher(response);
		if (activeConnection.find()) {
			return Integer.valueOf(activeConnection.group(1));
		}
		return 0;
	}

	private Integer reading(String response) {
		Matcher reading = Pattern.compile("Reading:\\s([0-9]{1,})").matcher(response);
		if (reading.find()) {
			return Integer.valueOf(reading.group(1));
		}
		return 0;
	}

	private Integer writing(String response) {
		Matcher writing = Pattern.compile("Writing:\\s([0-9]{1,})").matcher(response);
		if (writing.find()) {
			return Integer.valueOf(writing.group(1));
		}
		return 0;
	}

	private Integer waiting(String response) {
		Matcher waiting = Pattern.compile("Waiting:\\s([0-9]{1,})").matcher(response);
		if (waiting.find()) {
			return Integer.valueOf(waiting.group(1));
		}
		return 0;
	}
}
