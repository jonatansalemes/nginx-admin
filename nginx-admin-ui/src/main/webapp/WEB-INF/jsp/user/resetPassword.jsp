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
						<fmt:message key="title"></fmt:message> - <html:span name="version"></html:span>
					</html:small>
				</html:h1>
			</html:div>
		</html:block>

		<html:block>
		
			<html:form action="/user/reset" label="{password.recovery}"
				validation="/user/validateBeforeResetPassword">
				<html:formGroup label="{login}" required="true">
					<html:input name="login" type="email" required="true"
						placeholder="{login.placeholder}"></html:input>
				</html:formGroup>
				<html:toolbar>
					<html:button type="submit" label="{reset}" state="primary"></html:button>
				</html:toolbar>
			</html:form>
		</html:block>


		<html:block align="center">
			<html:link url="/user/login" label="{back}"></html:link>
		</html:block>

	</html:container>
	
	<ajax:function url="/version" name="version" executeOnDocumentLoad="true">
		<ajax:onSuccess>
			<ajax:target type="html" data="version" target="version"></ajax:target>
		</ajax:onSuccess>
	</ajax:function>

</html:view>