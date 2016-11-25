<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:block>
		<html:alert state="success" label="{nginx.update.success}" rendered="${ !empty(updated) && updated }"></html:alert>
	</html:block>

	<html:block>
		<html:form action="/nginx/update" validation="/nginx/validate" label="{nginx.settings}">
			<html:input name="id" value="${ nginx.id }" type="hidden"></html:input>
			<html:formGroup label="{nginx.bin}" required="true">
				<html:input name="bin" value="${ nginx.bin }"
					placeholder="{nginx.bin.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:toolbar>
				<html:button state="primary" type="submit" label="{nginx.save.settings}"></html:button>
			</html:toolbar>
			
		</html:form>
	</html:block>
	
</html:view>