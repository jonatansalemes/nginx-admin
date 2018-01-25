<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block rendered="${ nginxCommandLineInterfaceResponse != null }">
		
		<html:alert state="danger" rendered="${ nginxCommandLineInterfaceResponse.error() }">
			${ nginxCommandLineInterfaceResponse.stackTrace }
		</html:alert>
		
		<html:alert state="${ nginxCommandLineInterfaceResponse.success ? 'success' : 'danger' }" rendered="${ nginxCommandLineInterfaceResponse.success() }">
			${ nginxCommandLineInterfaceResponse.output }
		</html:alert>
		
	</html:block>

	<html:block>
		<html:card>
			<html:cardBody>
				<html:panel>
					<html:panelHead>
						<fmt:message key="nginx.operations">
							<fmt:param value="${ nginx.name }"></fmt:param>
							<fmt:param value="${ nginx.endpoint }"></fmt:param>
						</fmt:message>
					</html:panelHead>
					<html:panelBody>
						<html:buttonGroup spaced="true">

							<html:button url="/dashboard/testConfiguration/${ nginx.id }" state="success"
								label="{test.config}"></html:button>

							<html:button url="/dashboard/start/${ nginx.id }" state="success" label="{start}"></html:button>

							<html:button url="/dashboard/status/${ nginx.id }" state="info" label="{status}"></html:button>


							<html:button id="killAll" state="danger" label="{killAll}"></html:button>
							<html:confirm url="/dashboard/killAll/${ nginx.id }" attachTo="killAll"
								label="{killAll.confirm}">
							</html:confirm>

							<html:button id="stop" state="danger" label="{stop}"></html:button>
							<html:confirm url="/dashboard/stop/${ nginx.id }" attachTo="stop"
								label="{stop.confirm}">
							</html:confirm>

							<html:button id="restart" state="danger" label="{restart}"></html:button>
							<html:confirm url="/dashboard/restart/${ nginx.id }" attachTo="restart"
								label="{restart.confirm}">
							</html:confirm>

							<html:button id="reload" state="danger" label="{reload}"></html:button>
							<html:confirm url="/dashboard/reload/${ nginx.id }" attachTo="reload"
								label="{reload.confirm}">
							</html:confirm>


						</html:buttonGroup>
					</html:panelBody>
				</html:panel>
			</html:cardBody>
		</html:card>
	</html:block>


	<html:block>
		<html:row>
			<html:col size="4">
				<html:card>
					<html:cardBody>
						<html:panel>
							<html:panelHead label="{so.details}"></html:panelHead>
							<html:panelBody>
								<html:listGroup rendered="${ nginxOperationalSystemInfoResponse.success() }">
									<html:listGroupItem>
										<fmt:message key="so.arch" /> : ${ nginxOperationalSystemInfoResponse.architecture }</html:listGroupItem>
									<html:listGroupItem>
										<fmt:message key="so.name" /> : ${ nginxOperationalSystemInfoResponse.name }</html:listGroupItem>
									<html:listGroupItem>
										<fmt:message key="so.version" /> : ${ nginxOperationalSystemInfoResponse.version }</html:listGroupItem>
									<html:listGroupItem rendered="${ !empty(nginxOperationalSystemInfoResponse.distribution) }">
										<fmt:message key="so.distribution" /> : ${ nginxOperationalSystemInfoResponse.distribution }</html:listGroupItem>
								</html:listGroup>
								
								<html:alert state="danger" rendered="${ nginxOperationalSystemInfoResponse.error() }">
									${ nginxOperationalSystemInfoResponse.stackTrace }
								</html:alert>
								
							</html:panelBody>
						</html:panel>
					</html:cardBody>
				</html:card>
			</html:col>
			<html:col size="4">
				<html:card>
					<html:cardBody>
						<html:panel>
							<html:panelHead label="{nginx.status}"></html:panelHead>
							<html:panelBody>
								<html:listGroup>
									<html:listGroupItem><fmt:message key="nginx.active" /> : <html:span
											id="par_active_connection">0</html:span>
									</html:listGroupItem>
									<html:listGroupItem><fmt:message key="nginx.accepts" /> : <html:span
											id="par_accepts">0</html:span>
									</html:listGroupItem>
									<html:listGroupItem><fmt:message key="nginx.handled" /> : <html:span
											id="par_handled">0</html:span>
									</html:listGroupItem>
									<html:listGroupItem><fmt:message key="nginx.requests" /> : <html:span
											id="par_requests">0</html:span>
									</html:listGroupItem>
									<html:listGroupItem><fmt:message key="nginx.reading" /> : <html:span
											id="par_reading">0</html:span>
									</html:listGroupItem>
									<html:listGroupItem><fmt:message key="nginx.writing" /> : <html:span
											id="par_writing">0</html:span>
									</html:listGroupItem>
									<html:listGroupItem><fmt:message key="nginx.waiting" /> : <html:span
											id="par_waiting">0</html:span>
									</html:listGroupItem>
								</html:listGroup>
							</html:panelBody>
						</html:panel>
					</html:cardBody>
				</html:card>
			</html:col>
			<html:col size="4">
				<html:card>
					<html:cardBody>
						<html:panel>
							<html:panelHead label="{nginx.details}"></html:panelHead>
							<html:panelBody>
								<html:listGroup rendered="${ nginxServerInfoResponse.success() }">
									<html:listGroupItem><fmt:message key="nginx.version"></fmt:message> : ${ nginxServerInfoResponse.version }</html:listGroupItem>
									<html:listGroupItem><fmt:message key="nginx.address"></fmt:message> : ${ nginxServerInfoResponse.address }</html:listGroupItem>
									<html:listGroupItem><fmt:message key="nginx.pid"></fmt:message> :     ${ nginxServerInfoResponse.pid }</html:listGroupItem>
									<html:listGroupItem><fmt:message key="nginx.uptime"></fmt:message> :  ${ nginxServerInfoResponse.uptime } days</html:listGroupItem>
								</html:listGroup>
								
								<html:alert state="danger" rendered="${ nginxOperationalSystemInfoResponse.error() }">
									${ nginxOperationalSystemInfoResponse.stackTrace }
								</html:alert>
							</html:panelBody>
						</html:panel>
					</html:cardBody>
				</html:card>
			</html:col>
		</html:row>
	</html:block>


	<ajax:function url="/nginx/status" name="status"
		executeOnDocumentLoad="true">
		<ajax:onSuccess>
			<ajax:target type="html" data="nginxStatus.activeConnection"
				target="active.connection"></ajax:target>
			<ajax:target type="html" data="nginxStatus.accepts" target="accepts"></ajax:target>
			<ajax:target type="html" data="nginxStatus.handled" target="handled"></ajax:target>
			<ajax:target type="html" data="nginxStatus.requests"
				target="requests"></ajax:target>
			<ajax:target type="html" data="nginxStatus.reading" target="reading"></ajax:target>
			<ajax:target type="html" data="nginxStatus.writing" target="writing"></ajax:target>
			<ajax:target type="html" data="nginxStatus.waiting" target="waiting"></ajax:target>

		</ajax:onSuccess>
	</ajax:function>

	<html:jsCode>
		setInterval(function(){
			status();
		},10000);
	</html:jsCode>

</html:view>