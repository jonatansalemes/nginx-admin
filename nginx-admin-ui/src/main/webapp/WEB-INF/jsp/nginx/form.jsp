<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	
	<html:block>
		<html:alert state="success" label="{nginx.update.success}"
			rendered="${ operation == 'UPDATE' }"></html:alert>
		<html:alert state="success" label="{nginx.insert.success}"
			rendered="${ operation == 'INSERT' }"></html:alert>
	</html:block>

	<html:block>
		<html:form action="/nginx/saveOrUpdate" validation="/nginx/validate" label="{nginx.settings}">
			<html:input name="id" value="${ nginx.id }" type="hidden"></html:input>
			
			<html:formGroup>
				<html:panel>
					<html:panelHead label="{nginx.remote.settings}"></html:panelHead>
					<html:panelBody>
						<html:formGroup label="{nginx.name}" required="true">
							<html:input name="name" value="${ nginx.name }"
								placeholder="{nginx.name.placeholder}" required="true"></html:input>
						</html:formGroup>
						
						<html:formGroup label="{nginx.bin}" required="true">
							<html:input name="bin" value="${ nginx == null ? '/usr/sbin/nginx' : nginx.bin }"
								placeholder="{nginx.bin.placeholder}" required="true"></html:input>
						</html:formGroup>
						<html:formGroup label="{nginx.home.folder}" required="true">
							<html:input name="home" value="${ nginx == null ? '/opt/nginx/settings' : nginx.home }"
								placeholder="{nginx.home.folder.placeholder}" required="true"></html:input>
						</html:formGroup>
						<html:formGroup>
							<html:listGroup>
								<html:listGroupItem>
									<html:input checked="${ nginx == null ? 1 :  nginx.gzip }" name="gzip"
										value="1" type="checkbox"></html:input> <fmt:message key="nginx.enable.gzip"></fmt:message>
							</html:listGroupItem>
							</html:listGroup>
						</html:formGroup>
						<html:formGroup label="{nginx.max.post.size}" required="true">
							<html:input value="${ nginx == null ? 15 : nginx.maxPostSize }" name="maxPostSize" type="number" maxLength="9999"
										placeholder="{nginx.max.post.size.placeholder}" required="true"></html:input>
						</html:formGroup>
					</html:panelBody>
				</html:panel>
			</html:formGroup>
			
			<html:formGroup>
				<html:panel>
					<html:panelHead label="{nginx.agent.settings}"></html:panelHead>
					<html:panelBody>
						<html:formGroup label="{nginx.ip}" required="true">
							<html:input name="ip" value="${ nginx.ip }"
								placeholder="{nginx.ip.placeholder}" required="true"></html:input>
						</html:formGroup>
						<html:formGroup label="{nginx.port}" required="true">
							<html:input name="port" value="${ nginx == null ? 3000 : nginx.port }"
								placeholder="{nginx.port.placeholder}" type="number" required="true" max="99999"></html:input>
						</html:formGroup>
						<html:formGroup label="{nginx.authorization.key}" required="true">
							<html:input name="authorizationKey" value="${ nginx.authorizationKey }"
								placeholder="{nginx.authorization.key.placeholder}" required="true"></html:input>
						</html:formGroup>
					</html:panelBody>
				</html:panel>
			</html:formGroup>
			
			<html:toolbar>
				<html:button state="primary" type="submit" label="{nginx.save.settings}"></html:button>
			</html:toolbar>
		</html:form>
	</html:block>
	
	<html:block align="center">
		<html:link url="/nginx/list" label="{back}"></html:link>
	</html:block>
	
</html:view>