<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="success" label="{nginx.configure.update.success}"
			rendered="${ operation == 'UPDATE' }"></html:alert>
		<html:alert state="success" label="{nginx.configure.insert.success}"
			rendered="${ operation == 'INSERT' }"></html:alert>
		<html:alert state="danger" rendered="${ nginxConfigureResponse.error() }">
				${ nginxConfigureResponse.stackTrace }
			</html:alert>
	</html:block>
	
	<html:block>
		<html:form action="/configuration/saveOrUpdate" validation="/configuration/validate"
			label="{nginx.configure.form}">
			<html:input name="id" value="${ configuration.id }" type="hidden"></html:input>
			<html:input name="idNginx" type="hidden" value="${ nginx.id }"></html:input>
			<html:formGroup label="{nginx.configure.bin}" required="true">
				<html:input name="bin" value="${ configuration == null ? '/usr/sbin/nginx' : configuration.bin }"
					placeholder="{nginx.configure.bin.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{nginx.configure.home.folder}" required="true">
				<html:input name="home" value="${ configuration == null ? '/opt/nginx-agent/settings' : configuration.home }"
					placeholder="{nginx.configure.home.folder.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup>
				<html:listGroup>
					<html:listGroupItem>
						<html:input checked="${ configuration == null ? 1 : configuration.gzip }" name="gzip" value="1"
							type="checkbox"></html:input>
						<fmt:message key="nginx.configure.enable.gzip"></fmt:message>
					</html:listGroupItem>
				</html:listGroup>
			</html:formGroup>
			<html:formGroup label="{nginx.configure.max.post.size}" required="true">
				<html:input value="${ configuration == null ? 30 : configuration.maxPostSize }" name="maxPostSize"
					type="number" maxLength="9999"
					placeholder="{nginx.configure.max.post.size.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:toolbar>
				<html:button state="primary" type="submit"
					label="{nginx.configure.apply.settings}"></html:button>
			</html:toolbar>
		</html:form>
	</html:block>

</html:view>