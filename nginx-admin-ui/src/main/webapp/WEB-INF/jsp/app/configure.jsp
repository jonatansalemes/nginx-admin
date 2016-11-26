<%@include file="../app/taglibs.jsp"%>
<html:view>
	<html:container>
			<html:block>
				<html:alert state="warning">
					<fmt:message key="welcome.configuration"></fmt:message>
					<html:button state="success" label="{check.configuration}" url="/app/configure/check"></html:button>
				</html:alert>
			</html:block>
			<html:block rendered="${ !empty(invalids) }">
				<html:alert state="danger">
					<html:listGroup>
						<c:forEach items="${ invalids }" var="invalid">
							<html:listGroupItem>
								${ invalid }
							</html:listGroupItem>
						</c:forEach>
					</html:listGroup>
				</html:alert>
			</html:block>
			<html:block>
				<html:tabPanel>
					<html:tab active="true" url="/smtp/edit" label="{smtp.settings}"></html:tab>
					<html:tab url="/nginx/edit" label="{nginx.settings}"></html:tab>
					<html:tab url="/user/changePassword" label="{password.change}"></html:tab>
					<html:tab url="/user/changeLogin" label="{login.change}"></html:tab>
				</html:tabPanel>
			</html:block>
	</html:container>
</html:view>