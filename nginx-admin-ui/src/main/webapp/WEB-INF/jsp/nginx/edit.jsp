<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:block>
		<html:alert state="success" label="{nginx.update.success}" rendered="${ !empty(updated) && updated }"></html:alert>
	</html:block>

	<html:block>
		<html:form action="/nginx/update" validation="/nginx/validate" label="{nginx.settings}">
			<html:input name="id" value="${ nginx.id }" type="hidden"></html:input>
			<html:formGroup label="{nginx.username}" required="true">
				<html:input name="userName" value="${ nginx.userName }"
					placeholder="{nginx.username.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{nginx.groupname}" required="true">
				<html:input name="groupName" value="${ nginx.groupName }"
					placeholder="{nginx.groupname.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{nginx.home}" required="true">
				<html:input name="home" value="${ nginx.home }"
					placeholder="{nginx.home.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{nginx.bin}" required="true">
				<html:input name="bin" value="${ nginx.bin }"
					placeholder="{nginx.bin.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{nginx.conf}" required="true">
				<html:input name="conf" value="${ nginx.conf }"
					placeholder="{nginx.conf.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{nginx.error}" required="true">
				<html:input name="error" value="${ nginx.error }"
					placeholder="{nginx.error.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{nginx.access}" required="true">
				<html:input name="access" value="${ nginx.access }"
					placeholder="{nginx.access.placeholder}" required="true"></html:input>
			</html:formGroup>
			
			<html:toolbar>
				<html:button state="primary" type="submit" label="{nginx.save.settings}"></html:button>
			</html:toolbar>
			
		</html:form>
	</html:block>
	
	
	
</html:view>