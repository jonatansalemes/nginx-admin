package com.jslsolucoes.nginx.admin.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
@Entity
@Table(name = "access_log", schema = "admin")
public class AccessLog implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date_time")
	private Date timestamp;

	@Column(name = "remote_addr")
	@SerializedName(value = "remote_addr")
	private String remoteAddress;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nginx")
	private Nginx nginx;

	@Column(name = "body_bytes_sent")
	@SerializedName(value = "body_bytes_sent")
	private Long bodyBytesSent;

	@Column(name = "bytes_sent")
	@SerializedName(value = "bytes_sent")
	private Long bytesSent;

	@Column(name = "connection")
	private Long connection;

	@Column(name = "connection_request")
	@SerializedName(value = "connection_requests")
	private Long connectionRequest;

	@Column(name = "msec")
	private BigDecimal msec;

	@Column(name = "request")
	private String request;

	@Column(name = "status")
	private Integer status;

	@Column(name = "scheme")
	private String scheme;

	@Column(name = "request_length")
	@SerializedName(value = "request_length")
	private Long requestLength;

	@Column(name = "request_time")
	@SerializedName(value = "request_time")
	private BigDecimal requestTime;

	@Column(name = "request_method")
	@SerializedName(value = "request_method")
	private String requestMethod;

	@Column(name = "request_uri")
	@SerializedName(value = "request_uri")
	private String requestUri;

	@Column(name = "server_name")
	@SerializedName(value = "server_name")
	private String serverName;

	@Column(name = "server_port")
	@SerializedName(value = "server_port")
	private Integer serverPort;

	@Column(name = "server_protocol")
	@SerializedName(value = "server_protocol")
	private String serverProtocol;

	@Column(name = "http_referrer")
	@SerializedName(value = "http_referrer")
	private String httpReferrer;

	@Column(name = "http_user_agent")
	@SerializedName(value = "http_user_agent")
	private String httpUserAgent;

	@Column(name = "http_x_forwarded_for")
	@SerializedName(value = "http_x_forwarded_for")
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

	public Nginx getNginx() {
		return nginx;
	}

	public void setNginx(Nginx nginx) {
		this.nginx = nginx;
	}

}
