package com.jslsolucoes.nginx.admin.agent.resource.impl.status;

import javax.enterprise.inject.Vetoed;

@Vetoed
public class NginxStatus {

	private Integer activeConnection = 0;
	private Integer accepts = 0;
	private Integer handled = 0;
	private Integer requests = 0;
	private Integer reading = 0;
	private Integer writing = 0;
	private Integer waiting = 0;

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
