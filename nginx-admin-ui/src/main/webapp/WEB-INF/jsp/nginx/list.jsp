<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="success" label="{nginx.delete.success}"
			rendered="${ operation == 'DELETE' }"></html:alert>
	</html:block>

	<html:block>

		<html:grid data="${ nginxList }" var="nginx" label="{nginx.list}" paginate="false">
			
			<html:gridColumn label="{nginx.bin}" exportable="true">
				${ nginx.bin }
			</html:gridColumn>
			<html:gridColumn label="{nginx.home.folder}" exportable="true">
				${ nginx.home }
			</html:gridColumn>
			<html:gridColumn label="{nginx.enable.gzip}" exportable="true" booleanType="true">
				${ nginx.gzip }
			</html:gridColumn>
			<html:gridColumn label="{nginx.max.post.size}" exportable="true">
				${ nginx.maxPostSize }
			</html:gridColumn>
			<html:gridColumn label="{nginx.ip}" exportable="true">
				${ nginx.ip }
			</html:gridColumn>
			<html:gridColumn label="{nginx.port}" exportable="true">
				${ nginx.port }
			</html:gridColumn>
			<html:gridColumn label="{nginx.authorization.key}" exportable="true">
				${ nginx.authorizationKey }
			</html:gridColumn>
			<html:gridColumn>
				<html:buttonGroup spaced="true">
					<html:button icon="pencil" url="/nginx/edit/${ nginx.id }"></html:button>
					<html:confirm attachTo="${ nginx.id }" url="/nginx/delete/${ nginx.id }">
						<fmt:message key="nginx.delete.confirm">
							<fmt:param value="${ nginx.ip }"></fmt:param>
						</fmt:message>
					</html:confirm>
				</html:buttonGroup>
			</html:gridColumn>
			
			<html:toolbar>
				<html:button icon="plus" url="/nginx/form"></html:button>
			</html:toolbar>
			
		</html:grid>
	</html:block>

</html:view>