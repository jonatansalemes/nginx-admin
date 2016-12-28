<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">
	
	<html:row>
		<html:col size="4">
			<html:pieChart dataset="${ browsers }" label="{report.user.agent.statistics}"></html:pieChart>
		</html:col>
		<html:col size="8">
			<html:barChart dataset="${ ips }" label="{report.origin.statistics}"></html:barChart>
		</html:col>
	</html:row>
	
	
	
</html:view>