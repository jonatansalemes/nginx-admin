<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="success" label="{ssl.update.success}"
			rendered="${ operation == 'UPDATE' }"></html:alert>
		<html:alert state="danger" label="{ssl.update.failed}"
			rendered="${ operation == 'UPDATE_FAILED' }"></html:alert>
		<html:alert state="success" label="{ssl.insert.success}"
			rendered="${ operation == 'INSERT' }"></html:alert>
		<html:alert state="danger" label="{ssl.insert.failed}"
			rendered="${ operation == 'INSERT_FAILED' }"></html:alert>
	</html:block>

	<html:block>
		<html:form action="/sslCertificate/saveOrUpdate" multipart="true" 
		   label="{ssl.form}" validation="/sslCertificate/validate">
			<html:input name="id" type="hidden" value="${ sslCertificate.id }"></html:input>
			<html:input name="idNginx" type="hidden" value="${ nginx.id }"></html:input>
			
			<html:formGroup label="{ssl.download}" rendered="${ sslCertificate != null }">
					<html:div>
						<html:link target="_blank"
						url="/sslCertificate/download/${ nginx.id }/${ sslCertificate.resourceIdentifierCertificate.uuid }"
						label="{ssl.certificate.download}"></html:link>
					</html:div>
					<html:div>
					<html:link target="_blank"
							url="/sslCertificate/download/${ nginx.id }/${ sslCertificate.resourceIdentifierCertificatePrivateKey.uuid }"
							label="{ssl.certificate.key.download}"></html:link>
					</html:div>
			</html:formGroup>
			
			<html:formGroup label="{ssl.common.name}" required="true">
				<html:input name="commonName" value="${ sslCertificate.commonName }"
					placeholder="{ssl.common.name.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{ssl.certificate}"
				required="${ sslCertificate == null }">
				<html:input name="certificateFile"
					required="${ sslCertificate == null }" type="file"
					accept=".cer|.der|.crt|.pem"></html:input>
			</html:formGroup>
			<html:formGroup label="{ssl.certificate.key}"
				required="${ sslCertificate == null }">
				<html:input name="certificatePrivateKeyFile"
					required="${ sslCertificate == null }" type="file" 
					 accept=".pem|.key|.pfx|.p12"></html:input>
			</html:formGroup>
		</html:form>
	</html:block>

	<html:block align="center">
		<html:link url="/sslCertificate/list/${ nginx.id }" label="{back}"></html:link>
	</html:block>
</html:view>