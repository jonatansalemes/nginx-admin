<%@page isErrorPage="true"%>
<%@include file="taglibs.jsp"%>
<html:view>
	<html:block>
		<html:alert state="danger" label="{error}"></html:alert>
	</html:block>
	<html:block>
		<html:panel>
			<html:panelHead label="{error.details}"></html:panelHead>
			<html:panelBody>
				<html:listGroup>
					<html:listGroupItem>
						<html:div>
							<fmt:message key="error.request.uri"/> : 
						</html:div>
						<html:div>
							${pageContext.errorData.requestURI}
						</html:div>
					</html:listGroupItem>
					<html:listGroupItem>
						<html:div>
							<fmt:message key="error.status.code"/> : 
						</html:div>
						<html:div> 
							${pageContext.errorData.statusCode}
						</html:div>
					</html:listGroupItem>
					<html:listGroupItem>
						<html:div>
							<fmt:message key="error.stack.trace"/> :
						</html:div>
						<html:div>
							${ x:fullStackTrace(pageContext.exception) }
						</html:div>
					</html:listGroupItem>
				</html:listGroup>
			</html:panelBody>
		</html:panel>
	</html:block>
</html:view>