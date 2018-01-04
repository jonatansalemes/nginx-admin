<%@include file="taglibs.jsp"%>
<html:view title="{title}">

	<html:nav>
		<html:container>
			<html:brand url="/">
				<fmt:message key="brand"/> - <html:span name="version"></html:span>
			</html:brand>
			<html:menuButton icon="option-vertical" align="right">
				<html:menuItem url="#" label="${ userSession.user.login }" icon="user"></html:menuItem>
				<html:menuSeparator></html:menuSeparator>
				<html:menuItem url="/" icon="home" label="{home}"></html:menuItem>
				<html:menuItem target="content" url="/settings/home" icon="cog" label="{settings}"></html:menuItem>
				<html:menuItem target="content" url="/report/search" icon="object-align-bottom" label="{reports}"></html:menuItem>
				<html:menuItem url="/user/logout" icon="log-out" label="{logout}"></html:menuItem>
			</html:menuButton>
			<html:menuButton icon="cloud-upload" align="right">
				<html:menuItem target="content" url="/import/form" icon="copy" label="{import.nginx.conf}"></html:menuItem>
			</html:menuButton>
			<html:menuButton icon="briefcase" align="right">
				<html:menuItem target="content" url="/sslCertificate/list" icon="lock" label="{ssl.certificates}"></html:menuItem>
				<html:menuItem target="content" url="/server/list" icon="list" label="{servers}"></html:menuItem>
				<html:menuItem target="content" url="/upstream/list" icon="indent-left" label="{upstreams}"></html:menuItem>
				<html:menuItem target="content" url="/virtualHost/list" icon="globe" label="{virtual.hosts}"></html:menuItem>
				<html:menuItem target="content" url="/accessLog/list" icon="stats" label="{access.logs}"></html:menuItem>
				<html:menuItem target="content" url="/errorLog/list" icon="fire" label="{error.logs}"></html:menuItem>
			</html:menuButton>
		</html:container>
	</html:nav>

	<html:container>
		<html:block>
			<html:iframe url="/admin/dashboard" name="content"></html:iframe>
		</html:block>
	</html:container>
	
	<ajax:function url="/version" name="version" executeOnDocumentLoad="true">
		<ajax:onSuccess>
			<ajax:target type="html" data="version" target="version"></ajax:target>
		</ajax:onSuccess>
	</ajax:function>
	
</html:view>