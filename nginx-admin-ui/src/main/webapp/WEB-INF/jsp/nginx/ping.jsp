<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">
	<html:div rendered="${ nginxResponse.error() }">
		<html:block>
			<html:alert state="danger" label="{nginx.agent.connection.error}"></html:alert>
		</html:block>
		<html:block>
			<html:panel>
				<html:panelHead label="{nginx.agent.connection.error.stack.trace}"></html:panelHead>
				<html:panelBody>
					${ nginxResponse.stackTrace }
				</html:panelBody>
			</html:panel>
		</html:block>
	</html:div>
	<html:alert state="danger" rendered="${ nginxResponse.forbidden() }" label="{nginx.agent.authentication.error}"></html:alert>
	<html:alert state="success" rendered="${ nginxResponse.success() }" label="{nginx.agent.connection.success}"></html:alert>
</html:view>