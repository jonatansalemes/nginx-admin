<%@include file="../app/taglibs.jsp"%>
<html:view>
		<html:block>
			<html:alert state="warning" label="{login.changed}" rendered="${ !empty(loginChanged) && loginChanged }"></html:alert>
		</html:block>
		<html:block>
			<html:form action="/user/changeLoginFor" 
				label="{login.changing}" 
				 validation="/user/validateBeforeChangeLogin"> 
				<html:formGroup label="{password.old}" required="true">
					<html:input name="passwordOld" type="password" required="true"
						placeholder="{password.old.placeholder}"></html:input>
				</html:formGroup>
				<html:formGroup label="{login.new}" required="true">
					<html:input name="login" type="email" required="true"
						placeholder="{login.new.placeholder}"></html:input>
				</html:formGroup>
				<html:toolbar>
					<html:button type="submit" label="{login.change}" state="primary"></html:button>
				</html:toolbar>
			</html:form>
		</html:block>
</html:view>