<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">
	<html:tabPanel>
		<html:tab active="${ nginx == null || nginx.configuration != null }" label="{nginx.agent.form}" url="${ nginx != null ? x:concat('/nginx/edit/',nginx.id) : '/nginx/form' } "></html:tab>
		<html:tab rendered="${ nginx != null }" active="${ nginx.configuration == null }" state="${ nginx.configuration == null ? 'danger' : 'default' }"  label="{nginx.configure.form}" 
			url="/configuration/edit/${ nginx.id }"></html:tab>
	</html:tabPanel>
</html:view>