<%@include file="../app/taglibs.jsp"%>
<html:view>

		<html:block>
			<html:alert state="warning" label="{why.change.password}" rendered="${ userSession.user.passwordForceChange == 1 }"></html:alert>
			<html:alert state="warning" label="{password.changed}" rendered="${ !empty(passwordChanged) && passwordChanged }"></html:alert>
		</html:block>
		<html:block>
			<html:form action="/user/change" 
				label="{password.changing}" 
				 validation="/user/validateBeforeChangePassword"> 
				<html:input type="hidden" name="forced" value="${ forced }"></html:input>
				
				<html:formGroup label="{password.old}" required="true">
					<html:input name="passwordOld" type="password" required="true"
						placeholder="{password.old.placeholder}"></html:input>
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
</html:view>