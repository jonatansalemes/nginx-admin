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

public class NginxStatus {

	private Integer activeConnection;
	private Integer accepts;
	private Integer handled;
	private Integer requests;
	private Integer reading;
	private Integer writing;
	private Integer waiting;

	public NginxStatus() {

	}

	public NginxStatus(Integer activeConnection, Integer accepts, Integer handled, Integer requests, Integer reading,
			Integer writing, Integer waiting) {
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
