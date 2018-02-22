<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="success" label="{nginx.agent.delete}"
			rendered="${ operation == 'DELETE' }"></html:alert>
	</html:block>

	<html:block>

		<html:grid data="${ nginxList }" var="nginx" label="{nginx.agent.list}" paginate="false">
			
			<html:gridColumn label="{nginx.agent.name}" exportable="true" state="${ nginx.configuration == null ? 'danger' : 'default' }">
				${ nginx.name }
			</html:gridColumn>
			<html:gridColumn label="{nginx.agent.endpoint}" exportable="true" state="${ nginx.configuration == null ? 'danger' : 'default' }">
				${ nginx.endpoint }
			</html:gridColumn>
			<html:gridColumn label="{nginx.agent.authorization.key}" exportable="true" state="${ nginx.configuration == null ? 'danger' : 'default' }">
				${ nginx.authorizationKey }
			</html:gridColumn>
			<html:gridColumn state="${ nginx.configuration == null ? 'danger' : 'default' }">
				<html:buttonGroup spaced="true">
					<html:button icon="pencil" url="/nginx/tabs/${ nginx.id }"></html:button>
					<html:button state="danger" disabled="${ !empty(nginx.servers) }" id="${ nginx.id }" icon="trash" url="#"></html:button>
					
					<html:icon icon="question-sign" id="help${ nginx.id }" rendered="${ !empty(nginx.servers) }"></html:icon>
					
					<html:tooltip attachToSelector="#help${ nginx.id }" rendered="${ !empty(nginx.servers) }" 
						label="{nginx.agent.delete.disabled}">
					</html:tooltip>
					
					<html:confirm attachToSelector="#${ nginx.id }" url="/nginx/delete/${ nginx.id }">
						<fmt:message key="nginx.agent.delete.confirm">
							<fmt:param value="${ nginx.name }"></fmt:param>
							<fmt:param value="${ nginx.endpoint }"></fmt:param>
						</fmt:message>
					</html:confirm>
				</html:buttonGroup>
			</html:gridColumn>
			
			<html:toolbar>
				<html:button icon="plus" url="/nginx/tabs"></html:button>
			</html:toolbar>
			
		</html:grid>
	</html:block>

</html:view>