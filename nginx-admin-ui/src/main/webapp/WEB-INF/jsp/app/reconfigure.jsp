<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:container>
			
			<html:block>
				<html:alert state="warning" label="{why.reconfigure}"></html:alert>
			</html:block>
			<html:block>
				<html:form action="/app/reconfigured" 
					label="{reconfiguring}" 
					 validation="/app/validateBeforeReconfigure"> 
					
					<html:formGroup label="{login.new}" required="true">
						<html:input  type="email" name="login" required="true" placeholder="{login.new.placeholder}"></html:input>
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
						<html:button type="submit" label="{reconfigure}" state="primary"></html:button>
					</html:toolbar>
				</html:form>
			</html:block>
	</html:container>
</html:view>