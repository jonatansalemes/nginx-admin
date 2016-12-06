<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:block>
		<html:alert state="success" label="{upstream.delete.success}"
			rendered="${ operation == 'DELETE' }"></html:alert>
	</html:block>

	<html:block>

		<html:grid data="${ upstreamList }" var="upstream" label="{upstream.list}" paginate="false">
			<html:gridColumn label="{upstream.name}" exportable="true">
				${ upstream.name }
			</html:gridColumn>
			<html:gridColumn label="{strategy.name}" exportable="true">
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
			<html:gridColumn label="{virtual.domains}" collapsable="true">
				<html:grid data="${ upstream.virtualDomains }" var="virtualDomain" simple="true">
					<html:gridColumn label="{virtualDomain.domain}">
						${ virtualDomain.domain }
					</html:gridColumn>
				</html:grid>
			</html:gridColumn>
			<html:gridColumn>
				<html:buttonGroup spaced="true">
					<html:button icon="pencil" url="/upstream/edit/${ upstream.id }"></html:button>
					<html:button disabled="${ !empty(upstream.virtualDomains) }" state="danger" id="${ upstream.id }" icon="trash" url="#"></html:button>
					<html:confirm attachTo="${ upstream.id }" url="/upstream/delete/${ upstream.id }">
						<fmt:message key="upstream.delete.confirm">
							<fmt:param value="${ upstream.name }"></fmt:param>
						</fmt:message>
					</html:confirm>
				</html:buttonGroup>
			</html:gridColumn>
			
			<html:toolbar>
				<html:button icon="plus" url="/upstream/form"></html:button>
			</html:toolbar>
			
		</html:grid>
	</html:block>

</html:view>