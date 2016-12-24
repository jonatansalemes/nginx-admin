<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="success" label="{import.nginx.conf.success}"
			rendered="${ !empty(imported) && imported }"></html:alert>
	</html:block>

	<html:block>
		<html:form action="/import/execute" 
		 label="{import.nginx.conf.form}" validation="/import/validate">
			
			<html:formGroup label="{import.nginx.conf.location}" required="true">
				<html:input name="nginxConf" 
					placeholder="{import.nginx.conf.location.placeholder}" required="true"></html:input>
			</html:formGroup>
			
			
			<html:toolbar>
				<html:button label="{import.nginx.conf.import}" type="submit" state="primary"></html:button>
			</html:toolbar>
			
		</html:form>
	</html:block>

	
</html:view>