<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block rendered="${!empty(runtimeResult)}">
		<html:alert state="${ runtimeResult.runtimeResultType == 'ERROR' ? 'danger' : 'success'}" dismissible="true">
			<html:listGroup>
				<html:listGroupItem>
					${ runtimeResult.runtimeResultType }
				</html:listGroupItem>
				<html:listGroupItem>
					${ runtimeResult.output }
				</html:listGroupItem>
			</html:listGroup>
		</html:alert>
	</html:block>

	
	<html:card>
		<html:cardBody>
			<html:panel>
				<html:panelHead label="{so.details}"></html:panelHead>
				<html:panelBody>
					<html:listGroup>
						<html:listGroupItem> <fmt:message key="so.arch"/> : ${ so.arch }</html:listGroupItem>
						<html:listGroupItem> <fmt:message key="so.name"/> : ${ so.name }</html:listGroupItem>
						<html:listGroupItem> <fmt:message key="so.version"/> : ${ so.version }</html:listGroupItem>
						<html:listGroupItem rendered="${ !empty(so.distribution) }"> <fmt:message key="so.distribution"/> : ${ so.distribution }</html:listGroupItem>
					</html:listGroup>
				</html:panelBody>
			</html:panel>
		</html:cardBody>
		<html:toolbar>
			<html:buttonGroup spaced="true">
				
				<html:button url="/admin/testConfig" state="success"
					label="{test.config}"></html:button>
		
				<html:button url="/admin/start" state="success"
					label="{start}"></html:button>
					
				<html:button url="/admin/status" state="info"
					label="{status}"></html:button>
					
				<html:button id="stop" state="danger"
					label="{stop}"></html:button>
				<html:confirm url="/admin/stop"
					attachTo="stop" label="{stop.confirm}">
				</html:confirm>

				<html:button id="restart" state="danger"
					label="{restart}"></html:button>
				<html:confirm url="/admin/restart"
					attachTo="restart" label="{restart.confirm}">
				</html:confirm>
				
				<html:button id="reload" state="danger"
					label="{reload}"></html:button>
				<html:confirm url="/admin/reload"
					attachTo="reload" label="{reload.confirm}">
				</html:confirm>


			</html:buttonGroup>
		</html:toolbar>
	</html:card>
	
	<html:block>
		<html:row>
			<html:col size="6">
				<html:card>
					<html:cardBody>
						<html:panel>
							<html:panelHead label="Nginx status"></html:panelHead>
							<html:panelBody>
								<html:listGroup>
									<html:listGroupItem>Active : <html:span id="par_active_connection">0</html:span></html:listGroupItem>
									<html:listGroupItem>Accepts : <html:span id="par_accepts">0</html:span></html:listGroupItem>
									<html:listGroupItem>Handled : <html:span id="par_handled">0</html:span></html:listGroupItem>
									<html:listGroupItem>Requests : <html:span id="par_requests">0</html:span></html:listGroupItem>
									<html:listGroupItem>Reading : <html:span id="par_reading">0</html:span></html:listGroupItem>
									<html:listGroupItem>Writing : <html:span id="par_writing">0</html:span></html:listGroupItem>
									<html:listGroupItem>Waiting : <html:span id="par_waiting">0</html:span></html:listGroupItem>
								</html:listGroup>
							</html:panelBody>
						</html:panel>
					</html:cardBody>
				</html:card>
			</html:col>
			<html:col size="6">
				<html:card>
					<html:cardBody>
						<html:panel>
							<html:panelHead label="Nginx details"></html:panelHead>
							<html:panelBody>
								<html:listGroup>
									<html:listGroupItem>Version : ${ nginxDetail.version }</html:listGroupItem>
									<html:listGroupItem>Address : ${ nginxDetail.address }</html:listGroupItem>
									<html:listGroupItem>PID :     ${ nginxDetail.pid }</html:listGroupItem>
									<html:listGroupItem>Uptime :  ${ nginxDetail.uptime } days</html:listGroupItem>
								</html:listGroup>
							</html:panelBody>
						</html:panel>
					</html:cardBody>
				</html:card>
			</html:col>
		</html:row>
	</html:block>
	
	
	<ajax:function url="/nginx/status" name="status" executeOnDocumentLoad="true">
		<ajax:onSuccess>
			<ajax:target type="html" data="nginxStatus.activeConnection" target="active.connection"></ajax:target>
			<ajax:target type="html" data="nginxStatus.accepts" target="accepts"></ajax:target>
			<ajax:target type="html" data="nginxStatus.handled" target="handled"></ajax:target>
			<ajax:target type="html" data="nginxStatus.requests" target="requests"></ajax:target>
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