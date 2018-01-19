package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxStatusResponse {

	private Integer activeConnection;
	private Integer accepts;
	private Integer handled;
	private Integer requests;
	private Integer reading;
	private Integer writing;
	private Integer waiting;

	public NginxStatusResponse() {

	}

	public NginxStatusResponse(Integer activeConnection, Integer accepts, Integer handled, Integer requests,
			Integer reading, Integer writing, Integer waiting) {
		this.activeConnection = activeConnection;
		this.accepts = accepts;
		this.handled = handled;
		this.requests = requests;
		this.reading = reading;
		this.writing = writing;
		this.waiting = waiting;
	}

	public Integer getActiveConnection() {
		return activeConnection;
	}

	public void setActiveConnection(Integer activeConnection) {
		this.activeConnection = activeConnection;
	}

	public Integer getAccepts() {
		return accepts;
	}

	public void setAccepts(Integer accepts) {
		this.accepts = accepts;
	}

	public Integer getHandled() {
		return handled;
	}

	public void setHandled(Integer handled) {
		this.handled = handled;
	}

	public Integer getRequests() {
		return requests;
	}

	public void setRequests(Integer requests) {
		this.requests = requests;
	}

	public Integer getReading() {
		return reading;
	}

	public void setReading(Integer reading) {
		this.reading = reading;
	}

	public Integer getWriting() {
		return writing;
	}

	public void setWriting(Integer writing) {
		this.writing = writing;
	}

	public Integer getWaiting() {
		return waiting;
	}

	public void setWaiting(Integer waiting) {
		this.waiting = waiting;
	}
}
