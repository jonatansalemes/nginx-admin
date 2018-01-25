<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">
	<html:tabPanel>
		<html:tab active="true" label="{nginx.agent.form}" url="${ nginx != null ? x:concat('/nginx/edit/',nginx.id) : '/nginx/form' } "></html:tab>
		<html:tab rendered="${ nginx != null }" label="{nginx.configure.form}" url="/configuration/edit/${ nginx.id }"></html:tab>
	</html:tabPanel>
</html:view>