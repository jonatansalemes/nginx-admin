<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:block>
		<html:alert state="success" label="{server.delete.success}"
			rendered="${ operation == 'DELETE' }"></html:alert>
	</html:block>

	<html:block>

		<html:grid data="${ serverList }" var="server" label="{server.list}" paginate="false">
			<html:gridColumn label="{server.ip}" exportable="true">
				${ server.ip }
			</html:gridColumn>
			<html:gridColumn>
				<html:buttonGroup spaced="true">
					<html:button icon="pencil" url="/server/edit/${ server.id }"></html:button>
					<html:button state="danger" id="${ server.id }" icon="trash" url="#"></html:button>
					<html:confirm attachTo="${ server.id }" url="/server/delete/${ server.id }">
						<fmt:message key="server.delete.confirm">
							<fmt:param value="${ server.ip }"></fmt:param>
						</fmt:message>
					</html:confirm>
				</html:buttonGroup>
			</html:gridColumn>
			
			<html:toolbar>
				<html:button icon="plus" url="/server/form"></html:button>
			</html:toolbar>
			
		</html:grid>
	</html:block>

</html:view>