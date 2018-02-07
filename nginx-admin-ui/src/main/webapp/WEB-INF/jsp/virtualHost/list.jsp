<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="success" label="{virtualHost.delete.success}"
			rendered="${ operation == 'DELETE' }"></html:alert>
		<html:alert state="danger" label="{virtualHost.delete.failed}"
			rendered="${ operation == 'DELETE_FAILED' }"></html:alert>
	</html:block>

	<html:block>

		<html:grid
		 search="true" url="/virtualHost/list/${ nginx.id }"
		 data="${ virtualHostList }" var="virtualHost" label="{virtualHost.list}" paginate="false">
			<html:gridColumn label="{virtualHost.aliases}">
				${ virtualHost.fullAliases }
			</html:gridColumn>
			<html:gridColumn label="{virtualHost.https}" booleanType="true" exportable="true">
				${ virtualHost.https }
			</html:gridColumn>
			<html:gridColumn label="{ssl.common.name}" exportable="true">
				${ virtualHost.sslCertificate.commonName }
			</html:gridColumn>
			<html:gridColumn label="{virtualHost.locations}" collapsable="true">
				<html:grid data="${ virtualHost.locations }" var="virtualHostLocation" simple="true">
					<html:gridColumn label="{virtualHost.location}">
						${ virtualHostLocation.path }
					</html:gridColumn>
					<html:gridColumn label="{upstream.name}">
						${ virtualHostLocation.upstream.name }
					</html:gridColumn>
				</html:grid>
			</html:gridColumn>
			<html:gridColumn label="{virtualHost.download}">
				<html:link target="_blank"
						url="/virtualHost/download/${ nginx.id }/${ virtualHost.resourceIdentifier.uuid }"
						label="{virtualHost.download.file}"></html:link>
			</html:gridColumn>
			<html:gridColumn>
				<html:buttonGroup spaced="true">
					<html:button icon="pencil" url="/virtualHost/edit/${ nginx.id }/${ virtualHost.id }"></html:button>
					<html:button state="danger" id="${ virtualHost.id }" icon="trash" url="#"></html:button>
					<html:confirm attachTo="${ virtualHost.id }" url="/virtualHost/delete/${ nginx.id }/${ virtualHost.id }">
						<fmt:message key="virtualHost.delete.confirm">
							<c:forEach items="${ virtualHost.aliases }" var="virtualHostAlias">
								<fmt:param value="${ virtualHostAlias.alias }"></fmt:param>
							</c:forEach>
						</fmt:message>
					</html:confirm>
				</html:buttonGroup>
			</html:gridColumn>
			
			<html:toolbar>
				<html:button icon="plus" url="/virtualHost/form/${ nginx.id }"></html:button>
			</html:toolbar>
			
		</html:grid>
	</html:block>

</html:view>