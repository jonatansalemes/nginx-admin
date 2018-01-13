package com.jslsolucoes.nginx.admin.agent.model.response.model;

import java.math.BigDecimal;
import java.util.Date;

public class AccessLog {

	private Long id;

	private Date timestamp;

	private String remoteAddress;

	private Long bodyBytesSent;

	private Long bytesSent;

	private Long connection;

	private Long connectionRequest;

	private BigDecimal msec;

	private String request;

	private Integer status;

	private String scheme;

	private Long requestLength;

	private BigDecimal requestTime;

	private String requestMethod;

	private String requestUri;

	private String serverName;

	private Integer serverPort;

	private String serverProtocol;

	private String httpReferrer;

	private String httpUserAgent;

	private String httpXForwardedFor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public Long getBodyBytesSent() {
		return bodyBytesSent;
	}

	public void setBodyBytesSent(Long bodyBytesSent) {
		this.bodyBytesSent = bodyBytesSent;
	}

	public Long getBytesSent() {
		return bytesSent;
	}

	public void setBytesSent(Long bytesSent) {
		this.bytesSent = bytesSent;
	}

	public Long getConnection() {
		return connection;
	}

	public void setConnection(Long connection) {
		this.connection = connection;
	}

	public Long getConnectionRequest() {
		return connectionRequest;
	}

	public void setConnectionRequest(Long connectionRequest) {
		this.connectionRequest = connectionRequest;
	}

	public BigDecimal getMsec() {
		return msec;
	}

	public void setMsec(BigDecimal msec) {
		this.msec = msec;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public Long getRequestLength() {
		return requestLength;
	}

	public void setRequestLength(Long requestLength) {
		this.requestLength = requestLength;
	}

	public BigDecimal getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(BigDecimal requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerProtocol() {
		return serverProtocol;
	}

	public void setServerProtocol(String serverProtocol) {
		this.serverProtocol = serverProtocol;
	}

	public String getHttpReferrer() {
		return httpReferrer;
	}

	public void setHttpReferrer(String httpReferrer) {
		this.httpReferrer = httpReferrer;
	}

	public String getHttpUserAgent() {
		return httpUserAgent;
	}

	public void setHttpUserAgent(String httpUserAgent) {
		this.httpUserAgent = httpUserAgent;
	}

	public String getHttpXForwardedFor() {
		return httpXForwardedFor;
	}

	public void setHttpXForwardedFor(String httpXForwardedFor) {
		this.httpXForwardedFor = httpXForwardedFor;
	}

}
