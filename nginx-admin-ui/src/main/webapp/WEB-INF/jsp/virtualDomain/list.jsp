<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:block>
		<html:alert state="success" label="{virtualDomain.delete.success}"
			rendered="${ operation == 'DELETE' }"></html:alert>
	</html:block>

	<html:block>

		<html:grid data="${ virtualDomainList }" var="virtualDomain" label="{virtualDomain.list}" paginate="false">
			<html:gridColumn label="{virtualDomain.domain}" exportable="true">
				${ virtualDomain.domain }
			</html:gridColumn>
			<html:gridColumn label="{virtualDomain.https}" booleanType="true" exportable="true">
				${ virtualDomain.https }
			</html:gridColumn>
			<html:gridColumn label="{ssl.common.name}" exportable="true">
				${ virtualDomain.sslCertificate.commonName }
			</html:gridColumn>
			<html:gridColumn label="{upstream.name}" exportable="true">
				${ virtualDomain.upstream.name }
			</html:gridColumn>
			<html:gridColumn>
				<html:buttonGroup spaced="true">
					<html:button icon="pencil" url="/virtualDomain/edit/${ virtualDomain.id }"></html:button>
					<html:button state="danger" id="${ virtualDomain.id }" icon="trash" url="#"></html:button>
					<html:confirm attachTo="${ virtualDomain.id }" url="/virtualDomain/delete/${ virtualDomain.id }">
						<fmt:message key="virtualDomain.delete.confirm">
							<fmt:param value="${ virtualDomain.domain }"></fmt:param>
						</fmt:message>
					</html:confirm>
				</html:buttonGroup>
			</html:gridColumn>
			
			<html:toolbar>
				<html:button icon="plus" url="/virtualDomain/form"></html:button>
			</html:toolbar>
			
		</html:grid>
	</html:block>

</html:view>