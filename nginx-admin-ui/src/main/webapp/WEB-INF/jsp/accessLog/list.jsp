<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:grid data="${ accessLogList }" var="accessLog" label="{access.log.list}" 
	 totalResults="${ totalResults }" url="/accessLog/list/${ nginx.id }">
		<html:gridColumn label="{access.log.timestamp}" exportable="true" format="timestamp">
				${ accessLog.timestamp }
		</html:gridColumn>
		<html:gridColumn label="{access.log.remote.address}" exportable="true">
				${ accessLog.remoteAddress }
		</html:gridColumn>
		<html:gridColumn label="{access.log.request}" exportable="true">
				${ accessLog.request }
		</html:gridColumn>
		<html:gridColumn label="{access.log.user.agent}" exportable="true">
				${ accessLog.httpUserAgent }
		</html:gridColumn>
		<html:gridColumn label="{access.log.status}" exportable="true">
				${ accessLog.status }
		</html:gridColumn>
		<html:gridColumn label="{access.log.http.referrer}" exportable="true">
				${ accessLog.httpReferrer }
		</html:gridColumn>
		
		<html:gridColumn label="{access.log.request.info}" collapsable="true">
				<html:table>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.request"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.request }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.status"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.status }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.scheme"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.scheme }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.request.length"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.requestLength }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.request.time"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.requestTime }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.request.method"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.requestMethod }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.request.uri"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.requestUri }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.body.bytes.sent"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.bodyBytesSent }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.bytes.sent"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.bytesSent }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.connection"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.connection }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.connection.request"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.connectionRequest }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.msec"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.msec }</html:tableColumn>
					</html:tableLine>
				</html:table>
		</html:gridColumn>
		
		<html:gridColumn label="{access.log.server.info}" collapsable="true">
			<html:table>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.server.name"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.serverName }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.server.port"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.serverPort }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.server.protocol"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.serverProtocol }</html:tableColumn>
					</html:tableLine>
				</html:table>
		</html:gridColumn>
		
		<html:gridColumn label="{access.log.headers.info}" collapsable="true">
			<html:table>
					<html:tableLine>
						<html:tableColumn header="true"><fmt:message key="access.log.http.x.forwarded.for"></fmt:message></html:tableColumn>
						<html:tableColumn>${ accessLog.httpXForwardedFor }</html:tableColumn>
					</html:tableLine>
				</html:table>
		</html:gridColumn>	
	
	</html:grid>

</html:view>