<%@include file="../app/taglibs.jsp"%>
<html:view>

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
				<html:panelHead label="{machine.details}"></html:panelHead>
				<html:panelBody>
					<html:listGroup>
						<html:listGroupItem> <fmt:message key="machine.arch"/> : ${ so.arch }</html:listGroupItem>
						<html:listGroupItem> <fmt:message key="machine.os"/> : ${ so.name }</html:listGroupItem>
						<html:listGroupItem> <fmt:message key="machine.version"/> : ${ so.version }</html:listGroupItem>
						<html:listGroupItem> <fmt:message key="machine.vendor"/> : ${ so.operationalSystemDistribution.name }</html:listGroupItem>
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

</html:view>