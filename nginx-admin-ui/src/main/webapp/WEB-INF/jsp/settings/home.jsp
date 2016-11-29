<%@include file="../app/taglibs.jsp"%>
<html:view>
	<html:tabPanel>
		<html:tab active="true" label="{nginx.settings}" url="/nginx/edit"></html:tab>
		<html:tab label="{smtp.settings}" url="/smtp/edit"></html:tab>
		<html:tab label="{password.change}" url="/user/changePassword"></html:tab>
	</html:tabPanel>
</html:view>