<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:container>
	
		<html:block>
			<html:div>
				<html:img url="/image/logo.png" width="150" height="150"
					shape="circle" alt="logo" cssClass="center-block"></html:img>
			</html:div>
			<html:div cssClass="text-center">
				<html:h1>
					<html:small>
						<fmt:message key="title"></fmt:message> - ${ version }
					</html:small>
				</html:h1>
			</html:div>
		</html:block>
	
		<html:block>
			<html:alert state="info" label="{install.welcome}"></html:alert>
		</html:block>

		<html:block>
			<html:form action="/installer/install"
				label="{install.configuration}"
				validation="/installer/validateBeforeInstall">

				<html:formGroup>
					<html:panel>
						<html:panelHead label="{admin.settings}"></html:panelHead>
						<html:panelBody>
							<html:formGroup label="{admin.login}" required="true">
								<html:input name="login" type="email" required="true"
									placeholder="{admin.login.placeholder}"></html:input>
							</html:formGroup>
							<html:formGroup label="{admin.login.confirm}" required="true">
								<html:input name="loginConfirm" type="email" required="true"
									placeholder="{admin.login.confirm.placeholder}"></html:input>
							</html:formGroup>
							<html:formGroup label="{admin.password.new}" required="true">
								<html:input name="adminPassword" type="password" required="true"
									placeholder="{admin.password.new.placeholder}"></html:input>
							</html:formGroup>
							<html:formGroup label="{admin.password.confirm}" required="true">
								<html:input name="adminPasswordConfirm" type="password"
									required="true"
									placeholder="{admin.password.confirm.placeholder}"></html:input>
							</html:formGroup>
						</html:panelBody>
					</html:panel>
				</html:formGroup>

				<html:formGroup>
					<html:panel>
						<html:panelHead label="{nginx.settings}"></html:panelHead>
						<html:panelBody>
							<html:formGroup label="{nginx.bin}" required="true">
								<html:input name="nginxBin"
									placeholder="{nginx.bin.placeholder}" required="true"
									 value="/usr/sbin/nginx"></html:input>
							</html:formGroup>
							<html:formGroup label="{nginx.settings.folder}" required="true">
								<html:input name="nginxSettings"
									placeholder="{nginx.settings.folder.placeholder}" required="true" 
									 value="/opt/nginx-admin/settings"></html:input>
							</html:formGroup>
						</html:panelBody>
					</html:panel>
				</html:formGroup>
				
				<html:formGroup>
					<html:panel>
						<html:panelHead label="{app.settings}"></html:panelHead>
						<html:panelBody>
							<html:formGroup label="{app.settings.url.base}" required="true">
								<html:input name="urlBase"
									placeholder="{app.settings.url.base.placeholder}" required="true" 
									 value="${ urlBase }" disabled="true"></html:input>
							</html:formGroup>
						</html:panelBody>
					</html:panel>
				</html:formGroup>

				<html:formGroup>
					<html:panel>
						<html:panelHead label="{smtp.settings}"></html:panelHead>
						<html:panelBody>
							<html:formGroup label="{smtp.host}" required="true">
								<html:input name="smtpHost" placeholder="{smtp.host.placeholder}"
									required="true" value="localhost"></html:input>
							</html:formGroup>
							<html:formGroup label="{smtp.port}" required="true">
								<html:input type="number" max="99999" name="smtpPort"
									placeholder="{smtp.port.placeholder}" required="true" value="25"></html:input>
							</html:formGroup>
							<html:formGroup>
								<html:listGroup>
									<html:listGroupItem>
										<html:input name="smtpAuthenticate" value="1" type="checkbox"></html:input>
										 <fmt:message key="smtp.authenticate"></fmt:message>
									</html:listGroupItem>
								</html:listGroup>
							</html:formGroup>
							<html:formGroup label="{smtp.username}" visible="false"
								required="true">
								<html:input name="smtpUsername"
									placeholder="{smtp.username.placeholder}" cssClass="auth"
									required="true"></html:input>
							</html:formGroup>
							<html:formGroup label="{smtp.password}" visible="false"
								required="true">
								<html:input name="smtpPassword" type="password"
									placeholder="{smtp.password.placeholder}" cssClass="auth"
									required="true"></html:input>
							</html:formGroup>
							<html:formGroup>
								<html:listGroup>
									<html:listGroupItem>
										<html:input name="smtpTls" value="1" type="checkbox"></html:input>
										 <fmt:message key="smtp.tls"></fmt:message>
									</html:listGroupItem>
								</html:listGroup>
							</html:formGroup>
							<html:formGroup label="{smtp.from.address}" required="true">
								<html:input type="email" name="smtpFromAddress"
									placeholder="{smtp.from.address.placeholder}" required="true"
									  value="app@nginxadmin.localhost.com"></html:input>
							</html:formGroup>
						</html:panelBody>
					</html:panel>
				</html:formGroup>

				<html:toolbar>
					<html:button type="submit" label="{install.configure}"
						state="primary"></html:button>
				</html:toolbar>
			</html:form>
		</html:block>
	</html:container>

	<html:jsEvent attachTo="smtpAuthenticate" event="click">
		if($(this).is(':checked')){
			$('.auth').parent().show();
		} else {
			$('.auth').val('').parent().hide();
		}
	</html:jsEvent>


</html:view>