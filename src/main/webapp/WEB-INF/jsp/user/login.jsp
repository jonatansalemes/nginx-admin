<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:container>
		<html:form action="/user/authenticate" label="{authentication}">
			<html:formGroup>
				<html:alert state="danger"
					rendered="${ !empty(invalid) && invalid }"
					label="{authenticate.invalid}">
				</html:alert>
			</html:formGroup>
			<html:formGroup label="{login}" required="true">
				<html:input name="login" type="email" required="true" placeholder="{login.placeholder}"></html:input>
			</html:formGroup>
			<html:formGroup label="{password}" required="true">
				<html:input name="password" type="password" required="true" placeholder="{password.placeholder}"></html:input>
			</html:formGroup>
			<html:toolbar>
				<html:button type="submit" label="{authenticate}" state="primary"></html:button>
			</html:toolbar>
		</html:form>
	</html:container>

</html:view>