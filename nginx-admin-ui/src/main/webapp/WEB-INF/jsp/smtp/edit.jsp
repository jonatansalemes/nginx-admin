<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:block>
		<html:alert state="success" label="{smtp.update.success}" rendered="${ !empty(updated) && updated }"></html:alert>
		<html:alert state="${ mailStatus == 'SENDED' ? 'success' : 'danger' }" rendered="${ !empty(sended) && sended }">
			<fmt:message key="${ mailStatus == 'SENDED' ? 'smtp.test.sended.success' : 'smtp.test.sended.error' }">
				<fmt:param value="${to}"></fmt:param>
				<fmt:param value="${subject}"></fmt:param>
			</fmt:message>
		</html:alert>
	</html:block>

	<html:block>
		<html:form action="/smtp/update" label="{smtp.settings}">
			<html:input name="id" value="${ smtp.id }" type="hidden"></html:input>
			<html:formGroup label="{smtp.host}" required="true">
				<html:input name="host" value="${ smtp.host }"
					placeholder="{smtp.host.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{smtp.port}" required="true">
				<html:input type="number" max="99999" name="port"
					value="${ smtp.port }" placeholder="{smtp.port.placeholder}"
					required="true"></html:input>
			</html:formGroup>
			<html:formGroup>
				<html:listGroup>
					<html:listGroupItem>
						<html:input checked="${ smtp.authenticate }" name="authenticate"
							value="1" type="checkbox"></html:input> <fmt:message key="smtp.authenticate"></fmt:message>
				</html:listGroupItem>
				</html:listGroup>
			</html:formGroup>
			<html:formGroup label="{smtp.username}"
				visible="${ smtp.authenticate == 1 }" required="true">
				<html:input name="username" value="${ smtp.userName }"
					placeholder="{smtp.username.placeholder}" cssClass="auth" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{smtp.password}"
				visible="${ smtp.authenticate == 1 }" required="true">
				<html:input name="password" type="password"
					value="${ smtp.password }" placeholder="{smtp.password.placeholder}"
					cssClass="auth" required="true"></html:input>
			</html:formGroup>
			<html:formGroup>
				<html:listGroup>
					<html:listGroupItem>
						<html:input checked="${ smtp.tls }" name="tls" value="1"
							type="checkbox"></html:input> <fmt:message key="smtp.tls"></fmt:message>
				</html:listGroupItem>
				</html:listGroup>
			</html:formGroup>
			<html:formGroup label="{smtp.from.address}" required="true">
				<html:input type="email" name="fromAddress"
					value="${ smtp.fromAddress }" placeholder="{smtp.from.address.placeholder}"
					required="true"></html:input>
			</html:formGroup>
			
			<html:toolbar>
				<html:buttonGroup spaced="true">
					<html:button state="primary" type="submit" label="{smtp.save.settings}"></html:button>
					<html:button rendered="${ smtp != null }" state="info" id="test" label="{smtp.test.settings}" url="#"></html:button>
				</html:buttonGroup>
			</html:toolbar>
			
		</html:form>
	</html:block>
	
	
	<html:modal label="{smtp.test.settings}" attachTo="test">
		<html:form action="/smtp/test">
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

	<html:jsEvent attachTo="authenticate" event="click">
		if($(this).is(':checked')){
			$('.auth').parent().show();
		} else {
			$('.auth').val('').parent().hide();
		}
	</html:jsEvent>
</html:view>