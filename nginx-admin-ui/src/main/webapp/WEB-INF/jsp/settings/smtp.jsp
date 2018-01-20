<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="${ mailStatus == 'SENDED' ? 'success' : 'danger' }" rendered="${ sended }">
			<fmt:message key="${ mailStatus == 'SENDED' ? 'smtp.test.sended.success' : 'smtp.test.sended.error' }">
				<fmt:param value="${to}"></fmt:param>
				<fmt:param value="${subject}"></fmt:param>
			</fmt:message>
		</html:alert>
	</html:block>

	<html:block>
	
		<html:panel>
			<html:panelHead label="{smtp.settings}"></html:panelHead>
			<html:panelBody>
				<html:table>
					<html:tableLine>
						<html:tableColumn header="true" label="{smtp.host}"></html:tableColumn>
						<html:tableColumn>${ smtp.host }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true" label="{smtp.port}"></html:tableColumn>
						<html:tableColumn>${ smtp.port }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true" label="{smtp.authenticate}"></html:tableColumn>
						<html:tableColumn>
							<html:icon icon="ok" rendered="${ smtp.authenticate }"></html:icon>
							<html:icon icon="remove" rendered="${ !smtp.authenticate }"></html:icon>
						</html:tableColumn>
					</html:tableLine>
					<html:tableLine rendered="${ smtp.authenticate }">
						<html:tableColumn header="true" label="{smtp.username}"></html:tableColumn>
						<html:tableColumn>${ smtp.userName }</html:tableColumn>
					</html:tableLine>
					<html:tableLine rendered="${ smtp.authenticate }">
						<html:tableColumn header="true" label="{smtp.password}"></html:tableColumn>
						<html:tableColumn>******************</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true" label="{smtp.tls}"></html:tableColumn>
						<html:tableColumn>
							<html:icon icon="ok" rendered="${ smtp.tls }"></html:icon>
							<html:icon icon="remove" rendered="${ !smtp.tls }"></html:icon>
						</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true" label="{smtp.from.address}"></html:tableColumn>
						<html:tableColumn>${ smtp.fromAddress }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true" label="{smtp.from.name}"></html:tableColumn>
						<html:tableColumn>${ smtp.fromName }</html:tableColumn>
					</html:tableLine>
				</html:table>
			</html:panelBody>
			<html:panelFooter>
				<html:buttonGroup spaced="true">
					<html:button state="info" id="test" label="{smtp.test.settings}" url="#"></html:button>
				</html:buttonGroup>
			</html:panelFooter>
		</html:panel>
	</html:block>
	
	<html:modal label="{smtp.test.settings}" attachTo="test">
		<html:form action="/settings/smtp/test">
			<html:formGroup required="true" label="{smtp.test.to}">
				<html:input type="email" name="to" required="true" placeholder="{smtp.test.to.placeholder}"></html:input>
			</html:formGroup>
			<html:formGroup required="true" label="{smtp.test.subject}">
				<html:input name="subject" required="true" placeholder="{smtp.test.subject.placeholder}"></html:input>
			</html:formGroup>
			<html:formGroup required="true" label="{smtp.test.message}">
				<html:textarea name="message" required="true" placeholder="{smtp.test.message.placeholder}"></html:textarea>
			</html:formGroup>
			<html:toolbar>
				<html:button type="submit" label="{smtp.test.send}"></html:button>
			</html:toolbar>
		</html:form>
	</html:modal>

	
</html:view>