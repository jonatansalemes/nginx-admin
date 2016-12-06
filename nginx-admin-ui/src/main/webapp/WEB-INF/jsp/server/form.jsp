<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:block>
		<html:alert state="success" label="{server.update.success}"
			rendered="${ operation == 'UPDATE' }"></html:alert>
		<html:alert state="success" label="{server.insert.success}"
			rendered="${ operation == 'INSERT' }"></html:alert>
	</html:block>

	<html:block>
		<html:form action="/server/saveOrUpdate" 
		 label="{server.form}" validation="/server/validate">
			<html:input name="id" type="hidden" value="${ server.id }"></html:input>
			<html:formGroup label="{server.ip}" required="true">
				<html:input name="ip" value="${ server.ip }"
					placeholder="{server.ip.placeholder}" required="true"></html:input>
			</html:formGroup>
		</html:form>
	</html:block>

	<html:block align="center">
		<html:link url="/server/list" label="{back}"></html:link>
	</html:block>
</html:view>