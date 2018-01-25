<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	
	
	<html:form action="/nginx/saveOrUpdate" validation="/nginx/validate" label="{nginx.agent.form}">
		<html:input name="id" value="${ nginx.id }" type="hidden"></html:input>
		
		<html:formGroup label="{nginx.agent.name}" required="true">
			<html:input name="name" value="${ nginx.name }"
				placeholder="{nginx.agent.name.placeholder}" required="true"></html:input>
		</html:formGroup>
		<html:formGroup label="{nginx.agent.endpoint}" required="true">
			<html:input name="endpoint" value="${ nginx.endpoint }"
				placeholder="{nginx.agent.endpoint.placeholder}" required="true"></html:input>
		</html:formGroup>
		<html:formGroup label="{nginx.agent.authorization.key}" required="true">
			<html:input name="authorizationKey" value="${ nginx.authorizationKey }"
				placeholder="{nginx.agent.authorization.key.placeholder}" required="true"></html:input>
		</html:formGroup>
		
		<html:toolbar>
			<html:buttonGroup spaced="true">
				<html:button state="primary" type="submit" label="{nginx.agent.save.settings}"></html:button>
				<html:button url="javascript:testSettings()" state="info" type="button" label="{nginx.agent.test.settings}"></html:button>
			</html:buttonGroup>
			
		</html:toolbar>
	</html:form>
	
	
	<html:modal label="{nginx.agent.connection.result}" id="modalTestSettings">
		<html:div id="modalTestSettingsResultInfo">
		
		</html:div>
	</html:modal>
	
	<ajax:function name="testSettings" url="/nginx/ping" dataType="html">
		<ajax:parameters>
			<ajax:parameter src="endpoint" name="endpoint" type="val"></ajax:parameter>
			<ajax:parameter src="authorizationKey" name="authorizationKey" type="val"></ajax:parameter>
		</ajax:parameters>
		<ajax:onSuccess>
				$('#modalTestSettingsResultInfo').html(data);
				$('#modalTestSettings').modal('show');
		</ajax:onSuccess>
	</ajax:function>
	
	<html:block align="center">
		<html:link url="/nginx/list" label="{back}"></html:link>
	</html:block>
	
</html:view>