<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:block>
		<html:alert state="success" label="{virtualDomain.update.success}"
			rendered="${ operation == 'UPDATE' }"></html:alert>
		<html:alert state="success" label="{virtualDomain.insert.success}"
			rendered="${ operation == 'INSERT' }"></html:alert>
	</html:block>

	<html:block>
		<html:form action="/virtualDomain/saveOrUpdate"
			label="{virtualDomain.form}" validation="/virtualDomain/validate">
			<html:input name="id" type="hidden" value="${ virtualDomain.id }"></html:input>
			<html:input name="idResourceIdentifier" type="hidden" value="${ virtualDomain.resourceIdentifier.id }"></html:input>
			<html:formGroup label="{virtualDomain.domain}" required="true">
				<html:input name="domain" value="${ virtualDomain.domain }"
					placeholder="{virtualDomain.domain.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup>
				<html:listGroup>
					<html:listGroupItem>
						<html:input name="https" checked="${ virtualDomain.https }" value="1" type="checkbox"></html:input>
						 <fmt:message key="virtualDomain.https"></fmt:message>
					</html:listGroupItem>
				</html:listGroup>
			</html:formGroup>
			<html:formGroup label="{ssl.common.name}" required="true" visible="${ virtualDomain.https == 1  }">
				<html:select cssClass="ssl" required="true" name="idSslCertificate"
					value="${ virtualDomain.sslCertificate.id }" data="${ sslCertificateList }"
					var="sslCertificate">
					<html:option value="${ sslCertificate.id }">${ sslCertificate.commonName }</html:option>
				</html:select>
			</html:formGroup>
			<html:formGroup label="{upstream.name}" required="true">
				<html:select required="true" name="idUpstream"
					value="${ virtualDomain.upstream.id }" data="${ upstreamList }"
					var="upstream">
					<html:option value="${ upstream.id }">${ upstream.name }</html:option>
				</html:select>
			</html:formGroup>
		</html:form>
	</html:block>
	
	<html:jsEvent attachTo="https" event="click">
		if($(this).is(':checked')){
			$('.ssl').parent().show();
		} else {
			$('.ssl').val('').parent().hide();
		}
	</html:jsEvent>

	<html:block align="center">
		<html:link url="/virtualDomain/list" label="{back}"></html:link>
	</html:block>
</html:view>