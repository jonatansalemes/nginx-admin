<%@include file="../app/taglibs.jsp"%>
<html:view>
	<html:tabPanel>
		<html:tab active="true" label="{nginx.settings}" url="/template/edit"></html:tab>
		<html:tab label="{smtp.settings}" url="/smtp/edit"></html:tab>
	</html:tabPanel>
</html:view>