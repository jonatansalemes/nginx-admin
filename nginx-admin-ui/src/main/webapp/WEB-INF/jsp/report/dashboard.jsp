<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:row>
		<html:col size="4">
			<html:pieChart dataset="${ browsers }"
				label="{report.user.agent.statistics}"></html:pieChart>
		</html:col>
		<html:col size="8">
			<html:barChart dataset="${ origins }"
				label="{report.origin.statistics}" horizontal="true"></html:barChart>
		</html:col>
	</html:row>
	<html:row>
		<html:col size="4">
			<html:pieChart dataset="${ statuses }"
				label="{report.status.code.statistics}"></html:pieChart>
		</html:col>
		<html:col size="8">
		
		</html:col>
	</html:row>

</html:view>