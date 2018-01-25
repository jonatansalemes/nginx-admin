<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">
	
	<html:alert state="success" label="{nginx.agent.update.success}"
			rendered="${ operation == 'UPDATE' }"></html:alert>
	<html:alert state="success" label="{nginx.agent.insert.success}"
			rendered="${ operation == 'INSERT' }"></html:alert>
	
	<html:jsCode>
		window.setTimeout(function(){
			window.parent.self.location = URL_BASE + '/nginx/tabs/${ id }';
		},3000);
	</html:jsCode>
	
</html:view>