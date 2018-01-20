<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">
	<html:tabPanel>
		<html:tab active="true" label="{application.settings}" url="/settings/app"></html:tab>
		<html:tab label="{smtp.settings}" url="/settings/smtp"></html:tab>
		<html:tab label="{password.change}" url="/user/changePassword"></html:tab>
	</html:tabPanel>
</html:view>