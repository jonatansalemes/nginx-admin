<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:container>
			
			<html:block>
				<html:alert state="warning" label="{why.change.password}" rendered="${ userSession.user.passwordForceChange == 1 }"></html:alert>
				<html:alert state="warning" label="{password.changed}" rendered="${ !empty(passwordChanged) && passwordChanged }"></html:alert>
			</html:block>
			<html:block>
				<html:form action="/user/change" 
					label="{password.changing}" 
					 validation="/user/validateBeforeChangePassword"> 
					
					<html:formGroup label="{login}" required="true">
						<html:input  type="email" value="${ userSession.user.login }" name="login" required="true" placeholder="{login.placeholder}"></html:input>
					</html:formGroup>
					
					<html:formGroup label="{password.new}" required="true">
						<html:input name="password" type="password" required="true"
							placeholder="{password.new.placeholder}"></html:input>
					</html:formGroup>
					<html:formGroup label="{password.confirm}" required="true">
						<html:input name="passwordConfirm" type="password" required="true"
							placeholder="{password.confirm.placeholder}"></html:input>
					</html:formGroup>
					<html:toolbar>
						<html:button type="submit" label="{password.change}" state="primary"></html:button>
					</html:toolbar>
				</html:form>
			</html:block>
	</html:container>
</html:view>