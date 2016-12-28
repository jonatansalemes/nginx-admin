<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

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
			<html:formGroup label="{nginx.settings.folder}" required="true">
				<html:input name="settings" value="${ nginx.settings }"
					placeholder="{nginx.settings.folder.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup>
				<html:listGroup>
					<html:listGroupItem>
						<html:input checked="${ nginx.gzip }" name="gzip"
							value="1" type="checkbox"></html:input> <fmt:message key="nginx.enable.gzip"></fmt:message>
				</html:listGroupItem>
				</html:listGroup>
			</html:formGroup>
			<html:formGroup label="{nginx.max.post.size}" required="true">
				<html:input value="${ nginx.maxPostSize }" name="maxPostSize" type="number" maxLength="9999"
							placeholder="{nginx.max.post.size.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:toolbar>
				<html:button state="primary" type="submit" label="{nginx.save.settings}"></html:button>
			</html:toolbar>
		</html:form>
	</html:block>
	
</html:view>