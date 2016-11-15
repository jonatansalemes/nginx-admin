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
				<html:panelHead label="Instance details"></html:panelHead>
				<html:panelBody>
					<html:listGroup>
						<html:listGroupItem> Arch : ${ so.arch }</html:listGroupItem>
						<html:listGroupItem> Operational System : ${ so.name }</html:listGroupItem>
						<html:listGroupItem> Version : ${ so.version }</html:listGroupItem>
					</html:listGroup>
				</html:panelBody>
			</html:panel>
		</html:cardBody>
		<html:toolbar>
			<html:buttonGroup spaced="true">
				<html:button id="configure" state="warning"
					label="{configure}" disabled="true"></html:button>
				<html:confirm url="/admin/configure"
					attachTo="configure">
				</html:confirm>

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

				<html:button url="/admin/virtual/domain"
					label="{virtual.domain.create}"></html:button>
				<html:button url="/admin/upstream"
					label="{upstream.create}"></html:button>
			</html:buttonGroup>
		</html:toolbar>
	</html:card>

</html:view>