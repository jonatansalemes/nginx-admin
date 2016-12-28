<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="success" label="{app.settings.update.success}"
			rendered="${ !empty(updated) && updated }"></html:alert>
	</html:block>

	<html:block>
		<html:form action="/app/update" 
			label="{app.settings}">
			<html:formGroup label="{app.settings.url.base}" required="true">
				<html:input name="urlBase" placeholder="{app.settings.url.base.placeholder}"
					required="true" value="${ urlBase }"></html:input>
			</html:formGroup>
			<html:toolbar>
				<html:button state="primary" type="submit"
					label="{app.settings.save}"></html:button>
			</html:toolbar>
		</html:form>
	</html:block>

</html:view>