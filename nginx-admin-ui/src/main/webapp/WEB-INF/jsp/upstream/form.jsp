<%@include file="../app/taglibs.jsp"%>
<html:view>

	<html:block>
		<html:alert state="success" label="{upstream.update.success}"
			rendered="${ operation == 'UPDATE' }"></html:alert>
		<html:alert state="success" label="{upstream.insert.success}"
			rendered="${ operation == 'INSERT' }"></html:alert>
	</html:block>

	<html:block>
		<html:form action="/upstream/saveOrUpdate" 
		 label="{upstream.form}" validation="/upstream/validate">
			<html:input name="id" type="hidden" value="${ upstream.id }"></html:input>
			<html:input name="idResourceIdentifier" type="hidden" value="${ upstream.resourceIdentifier.id }"></html:input>
			<html:formGroup label="{upstream.name}" required="true">
				<html:input name="name" value="${ upstream.name }"
					placeholder="{upstream.name.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{strategy.name}" required="true">
				<html:select required="true" name="idStrategy" value="${ upstream.strategy.id }" data="${ strategyList }" var="strategy">
					<html:option value="${ strategy.id }">${ strategy.name }</html:option>
				</html:select>
			</html:formGroup>
			<html:formGroup>
				<html:detailTable atLeast="1" data="${ upstream.servers }" var="upstreamServer" label="{upstream.servers}">
					<html:detailTableColumn label="{server.ip}" required="true">
						<html:select value="${ upstreamServer.server.id }" required="true" name="servers[]" data="${ serverList }" var="server">
							<html:option value="${ server.id }">${ server.ip }</html:option>
						</html:select>
					</html:detailTableColumn>
					<html:detailTableColumn label="{upstream.server.port}" required="true">
						<html:input value="${ upstreamServer.port }" name="ports[]" type="number" maxLength="99999"
							placeholder="{upstream.server.port.placeholder}" required="true"></html:input>
					</html:detailTableColumn>
				</html:detailTable>
			</html:formGroup>
		</html:form>
	</html:block>

	<html:block align="center">
		<html:link url="/upstream/list" label="{back}"></html:link>
	</html:block>
</html:view>