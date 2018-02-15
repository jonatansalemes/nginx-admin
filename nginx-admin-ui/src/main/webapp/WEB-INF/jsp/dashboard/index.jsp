<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:card>
			<html:cardBody>
				<html:block rendered="${ nginxCommandLineInterfaceResponse != null }">
					<html:panel>
							<html:panelHead label="{nginx.operation.panel}"/>
							<html:panelBody>
								<html:alert state="danger" rendered="${ nginxCommandLineInterfaceResponse.error() }" 
									label="{nginx.agent.connection.error}"/>
									
								<html:alert state="${ nginxCommandLineInterfaceResponse.success() 
										&& nginxCommandLineInterfaceResponse.success ? 'success' : 'danger' }" 
											rendered="${ nginxCommandLineInterfaceResponse.success() }">
											
											
											<html:span rendered="${ stop && nginxCommandLineInterfaceResponse.success }" label="{stop.ok}"></html:span>
											<html:span rendered="${ stop && !nginxCommandLineInterfaceResponse.success }" label="{stop.error}"></html:span>
											
											<html:span rendered="${ reload && nginxCommandLineInterfaceResponse.success }" label="{reload.ok}"></html:span>
											<html:span rendered="${ reload && !nginxCommandLineInterfaceResponse.success }" label="{reload.error}"></html:span>
											
											<html:span rendered="${ start && nginxCommandLineInterfaceResponse.success }" label="{start.ok}"></html:span>
											<html:span rendered="${ start && !nginxCommandLineInterfaceResponse.success }" label="{start.error}"></html:span>
											
											<html:span rendered="${ killAll && nginxCommandLineInterfaceResponse.success }" label="{killAll.ok}"></html:span>
											<html:span rendered="${ killAll && !nginxCommandLineInterfaceResponse.success }">
												<fmt:message key="killAll.error">
													<fmt:param value="${ nginxCommandLineInterfaceResponse.output }"></fmt:param>
												</fmt:message>
											</html:span>
											
											<html:span rendered="${ status && nginxCommandLineInterfaceResponse.success }" label="{status.ok}"></html:span>
											<html:span rendered="${ status && !nginxCommandLineInterfaceResponse.success }" label="{status.error}"></html:span>
											
											<html:span rendered="${ restart && nginxCommandLineInterfaceResponse.success }" label="{restart.ok}"></html:span>
											<html:span rendered="${ restart && !nginxCommandLineInterfaceResponse.success }" label="{restart.error}"></html:span>
											
											
											<html:span rendered="${ testConfiguration && nginxCommandLineInterfaceResponse.success }" label="{testConfiguration.ok}"></html:span>
											<html:span rendered="${ testConfiguration && !nginxCommandLineInterfaceResponse.success }">
												<fmt:message key="testConfiguration.error">
													<fmt:param value="${ nginxCommandLineInterfaceResponse.output }"></fmt:param>
												</fmt:message>
											</html:span>
											
								</html:alert>
							</html:panelBody>
					</html:panel>
				</html:block>
				<html:block>
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
								<html:confirm url="/dashboard/killAll/${ nginx.id }" attachToSelector="#killAll"
									label="{killAll.confirm}">
								</html:confirm>
	
								<html:button id="stop" state="danger" label="{stop}"></html:button>
								<html:confirm url="/dashboard/stop/${ nginx.id }" attachToSelector="#stop"
									label="{stop.confirm}">
								</html:confirm>
	
								<html:button id="restart" state="danger" label="{restart}"></html:button>
								<html:confirm url="/dashboard/restart/${ nginx.id }" attachToSelector="#restart"
									label="{restart.confirm}">
								</html:confirm>
	
								<html:button id="reload" state="danger" label="{reload}"></html:button>
								<html:confirm url="/dashboard/reload/${ nginx.id }" attachToSelector="#reload"
									label="{reload.confirm}">
								</html:confirm>
	
	
							</html:buttonGroup>
						</html:panelBody>
					</html:panel>
				</html:block>
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
								<html:alert state="danger" rendered="${ nginxOperationalSystemInfoResponse.error() }" label="{nginx.agent.connection.error}"/>
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
								<html:listGroup rendered="${ nginxStatusResponse.success() }">
									<html:listGroupItem>
										<fmt:message key="nginx.active">
											<fmt:param value="${ nginxStatusResponse.activeConnection }"></fmt:param>
										</fmt:message>
									</html:listGroupItem>
									<html:listGroupItem>
										<fmt:message key="nginx.accepts">
											<fmt:param value="${ nginxStatusResponse.accepts }"></fmt:param>
										</fmt:message>
									</html:listGroupItem>
									<html:listGroupItem>
										<fmt:message key="nginx.handled">
											<fmt:param value="${ nginxStatusResponse.handled }"></fmt:param>
										</fmt:message>
									</html:listGroupItem>
									<html:listGroupItem>
										<fmt:message key="nginx.requests">
											<fmt:param value="${ nginxStatusResponse.requests }"></fmt:param>
										</fmt:message>
									</html:listGroupItem>
									<html:listGroupItem>
										<fmt:message key="nginx.reading">
											<fmt:param value="${ nginxStatusResponse.reading }"></fmt:param>
										</fmt:message>
									</html:listGroupItem>
									<html:listGroupItem>
										<fmt:message key="nginx.writing">
											<fmt:param value="${ nginxStatusResponse.writing }"></fmt:param>
										</fmt:message>
									</html:listGroupItem>
									<html:listGroupItem>
										<fmt:message key="nginx.waiting">
											<fmt:param value="${ nginxStatusResponse.waiting }"></fmt:param>
										</fmt:message>
									</html:listGroupItem>
								</html:listGroup>
								<html:alert state="danger" rendered="${ nginxStatusResponse.error() }" label="{nginx.agent.connection.error}"/>
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
								<html:alert state="danger" rendered="${ nginxOperationalSystemInfoResponse.error() }" label="{nginx.agent.connection.error}"/>
							</html:panelBody>
						</html:panel>
					</html:cardBody>
				</html:card>
			</html:col>
		</html:row>
	</html:block>

</html:view>