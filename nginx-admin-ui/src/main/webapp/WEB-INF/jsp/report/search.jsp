<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:form label="{report.search}" action="/report/export.pdf" 
		  validation="/report/validate" target="_newtab">
		  	<html:input type="hidden" name="idNginx" value="${ nginx.id }"></html:input>
			<html:formGroup label="{report.server.name}">
				<html:listGroup>
					<c:forEach var="virtualHostAlias" items="${ virtualHostAliasList }" varStatus="status">
						<html:listGroupItem>
							<html:input name="aliases[${ status.index }]" type="checkbox" value="${ virtualHostAlias.id }"></html:input> ${ virtualHostAlias.alias }
						</html:listGroupItem>
					</c:forEach>
				</html:listGroup>
			</html:formGroup>
			<html:formGroup>
				<html:row>
					<html:col size="6">
						<html:formGroup label="{report.from}">
							<html:row>
								<html:col size="6">
									<html:input name="from" required="true"></html:input>
									<html:mask mask="99/99/9999" attachTo="from"></html:mask>
									<html:datePicker attachTo="from"></html:datePicker>
								</html:col>
								<html:col size="6">
									<html:input name="fromTime"></html:input>
									<html:timePicker attachTo="fromTime"></html:timePicker>
								</html:col>
							</html:row>
						</html:formGroup>
					</html:col>
					<html:col size="6">
						<html:formGroup label="{report.to}">
							<html:row>
								<html:col size="6">
									<html:input name="to" required="true"></html:input>
									<html:mask mask="99/99/9999" attachTo="to"></html:mask>
									<html:datePicker attachTo="to"></html:datePicker>
								</html:col>
								<html:col size="6">
									<html:input name="toTime"></html:input>
									<html:timePicker attachTo="toTime"></html:timePicker>
								</html:col>
							</html:row>
						</html:formGroup>
					</html:col>
				</html:row>
			</html:formGroup>
			<html:toolbar>
				<html:button type="submit" label="{report.export}" state="primary"></html:button>
			</html:toolbar>
		</html:form>
	</html:block>
	
</html:view>