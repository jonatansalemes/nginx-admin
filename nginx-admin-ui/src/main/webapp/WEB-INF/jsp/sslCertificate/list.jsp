<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="success" label="{ssl.delete.success}"
			rendered="${ operation == 'DELETE' }"></html:alert>
		<html:alert state="danger" label="{ssl.delete.failed}"
			rendered="${ operation == 'DELETE_FAILED' }"></html:alert>
	</html:block>

	<html:block>

		<html:grid data="${ sslCertificateList }" var="sslCertificate" label="{ssl.list}" paginate="false">
			<html:gridColumn label="{ssl.common.name}" exportable="true">
				${ sslCertificate.commonName }
			</html:gridColumn>
			<html:gridColumn label="{ssl.certificate}">
				<html:link target="_blank"
						url="/sslCertificate/download/${ nginx.id }/${ sslCertificate.resourceIdentifierCertificate.uuid }"
						label="{ssl.certificate.download}"></html:link>
			</html:gridColumn>
			<html:gridColumn label="{ssl.certificate.key}">
				<html:link target="_blank"
						url="/sslCertificate/download/${ nginx.id }/${ sslCertificate.resourceIdentifierCertificatePrivateKey.uuid }"
						label="{ssl.certificate.key.download}"></html:link>
			</html:gridColumn>
			<html:gridColumn>
				<html:buttonGroup spaced="true">
					<html:button icon="pencil" url="/sslCertificate/edit/${ nginx.id }/${ sslCertificate.id }"></html:button>
					<html:button disabled="${ !empty(sslCertificate.virtualHosts) }" state="danger" id="${ sslCertificate.id }" icon="trash" url="#"></html:button>
					
					
					<html:icon icon="question-sign" id="help${ sslCertificate.id }" rendered="${ !empty(sslCertificate.virtualHosts) }"></html:icon>
					
					<html:tooltip attachToSelector="#help${ sslCertificate.id }" rendered="${ !empty(sslCertificate.virtualHosts) }" 
						label="{ssl.delete.disabled}">
					</html:tooltip>
					
					<html:confirm attachToSelector="#${ sslCertificate.id }" url="/sslCertificate/delete/${ nginx.id }/${ sslCertificate.id }">
						<fmt:message key="ssl.delete.confirm">
							<fmt:param value="${ sslCertificate.commonName }"></fmt:param>
						</fmt:message>
					</html:confirm>
				</html:buttonGroup>
			</html:gridColumn>
			
			<html:toolbar>
				<html:button icon="plus" url="/sslCertificate/form/${ nginx.id }"></html:button>
			</html:toolbar>
			
		</html:grid>
	</html:block>

</html:view>