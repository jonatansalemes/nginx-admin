<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="success" label="{server.delete.success}"
			rendered="${ operation == 'DELETE' }"></html:alert>
	</html:block>

	<html:block>

		<html:grid data="${ serverList }" var="server" label="{server.list}" paginate="false">
			<html:gridColumn label="{server.ip}" exportable="true">
				${ server.ip }
			</html:gridColumn>
			<html:gridColumn>
				<html:buttonGroup spaced="true">
					<html:button icon="pencil" url="/server/edit/${nginx.id }/${ server.id }"></html:button>
					<html:button disabled="${ !empty(server.upstreamServers) }" state="danger" id="${ server.id }" icon="trash" url="#"></html:button>
					
					<html:icon icon="question-sign" id="help${ server.id }" rendered="${ !empty(server.upstreamServers) }"></html:icon>
					
					<html:tooltip attachToSelector="#help${ server.id }" rendered="${ !empty(server.upstreamServers) }" 
						label="{server.delete.disabled}">
					</html:tooltip>
					
					<html:confirm attachToSelector="#${ server.id }" url="/server/delete/${nginx.id }/${ server.id }">
						<fmt:message key="server.delete.confirm">
							<fmt:param value="${ server.ip }"></fmt:param>
						</fmt:message>
					</html:confirm>
				</html:buttonGroup>
			</html:gridColumn>
			
			<html:toolbar>
				<html:button icon="plus" url="/server/form/${nginx.id }"></html:button>
			</html:toolbar>
			
		</html:grid>
	</html:block>

</html:view>