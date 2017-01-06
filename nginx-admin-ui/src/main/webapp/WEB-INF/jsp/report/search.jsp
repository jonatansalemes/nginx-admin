<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:form label="{report.search}" action="/report/export.pdf" 
		  validation="/report/validate" target="_newtab">
			<html:formGroup label="{report.server.name}">
				<html:select name="idVirtualHost"
					data="${ virtualHostList }" var="virtualHost">
					<html:option value="${ virtualHost.id }">
						${ virtualHost.fullAliases }
					</html:option>
				</html:select>
			</html:formGroup>
			<html:formGroup>
				<html:row>
					<html:col size="6">
						<html:formGroup label="{report.from}">
							<html:row>
								<html:col size="6">
									<html:input name="from"></html:input>
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
									<html:input name="to"></html:input>
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