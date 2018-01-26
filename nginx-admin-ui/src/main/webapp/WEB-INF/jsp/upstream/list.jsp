<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="success" label="{upstream.delete.success}"
			rendered="${ operation == 'DELETE' }"></html:alert>
		<html:alert state="danger" label="{upstream.delete.failed}"
			rendered="${ operation == 'DELETE_FAILED' }"></html:alert>
	</html:block>

	<html:block>

		<html:grid data="${ upstreamList }" var="upstream" label="{upstream.list}" paginate="false">
			<html:gridColumn label="{upstream.name}" exportable="true">
				${ upstream.name }
			</html:gridColumn>
			<html:gridColumn label="{upstream.strategy.name}" exportable="true">
				${ upstream.strategy.name }
			</html:gridColumn>
			<html:gridColumn label="{upstream.servers}" collapsable="true">
				<html:grid data="${ upstream.servers }" var="upstreamServer" simple="true">
					<html:gridColumn label="{server.ip}">
						${ upstreamServer.server.ip }
					</html:gridColumn>
					<html:gridColumn label="{upstream.server.port}">
						${ upstreamServer.port }
					</html:gridColumn>
				</html:grid>
			</html:gridColumn>
			<html:gridColumn label="{upstream.download}">
				<html:link target="_blank"
						url="/upstream/download/${ nginx.id }/${ upstream.resourceIdentifier.uuid }"
						label="{upstream.download.file}"></html:link>
			</html:gridColumn>
			<html:gridColumn>
				<html:buttonGroup spaced="true">
					<html:button icon="pencil" url="/upstream/edit/${ nginx.id }/${ upstream.id }"></html:button>
					<html:button disabled="${ !empty(upstream.virtualHostLocations) }" state="danger" id="${ upstream.id }" icon="trash" url="#"></html:button>
					<html:confirm attachTo="${ upstream.id }" url="/upstream/delete/${ nginx.id }/${ upstream.id }">
						<fmt:message key="upstream.delete.confirm">
							<fmt:param value="${ upstream.name }"></fmt:param>
						</fmt:message>
					</html:confirm>
				</html:buttonGroup>
			</html:gridColumn>
			
			<html:toolbar>
				<html:button icon="plus" url="/upstream/form/${ nginx.id }"></html:button>
			</html:toolbar>
			
		</html:grid>
	</html:block>

</html:view>