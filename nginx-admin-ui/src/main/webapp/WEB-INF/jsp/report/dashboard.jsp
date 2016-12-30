<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:form label="{report.search}" action="/report/dashboard" 
		  validation="/report/validate">
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
									<html:timePicker attachTo="fromTime" pattern="hh:mm TT"></html:timePicker>
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
									<html:timePicker attachTo="toTime" pattern="hh:mm TT"></html:timePicker>
								</html:col>
							</html:row>
						</html:formGroup>
					</html:col>
				</html:row>
			</html:formGroup>
			<html:toolbar>
				<html:button type="submit" label="{report.search}" state="primary"></html:button>
			</html:toolbar>
		</html:form>
	</html:block>
	
	<html:block rendered="${ browsers == null && origins == null && statuses == null  }">
		<html:alert state="warning" label="{report.no.data}"></html:alert>
	</html:block>

	<html:block>
		<html:row>
			<html:col size="4">
				<html:pieChart dataset="${ browsers }"
					label="{report.user.agent.statistics}" rendered="${ browsers!=null  }"></html:pieChart>
			</html:col>
			<html:col size="8">
				<html:barChart dataset="${ origins }"
					label="{report.origin.statistics}" horizontal="true" rendered="${ origins!=null  }"></html:barChart>
			</html:col>
		</html:row>
		<html:row>
			<html:col size="4">
				<html:pieChart dataset="${ statuses }"
					label="{report.status.code.statistics}" rendered="${ statuses!=null  }"></html:pieChart>
			</html:col>
			<html:col size="8">
				
			</html:col>
		</html:row>
	</html:block>
	
</html:view>