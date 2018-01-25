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
				<html:menuItem target="content" url="/nginx/list" icon="equalizer" label="{nginxs}"></html:menuItem>
				<html:menuItem url="/" icon="home" label="{home}"></html:menuItem>
				<html:menuItem target="content" url="/settings/home" icon="cog" label="{settings}"></html:menuItem>
				<html:menuItem url="/user/logout" icon="log-out" label="{logout}"></html:menuItem>
			</html:menuButton>
			<html:menuButton icon="briefcase" align="right" rendered="${ nginx != null }">
				<html:menuItem url="#" label="${ nginx.name } (${ nginx.endpoint })" icon="stop"></html:menuItem>
				<html:menuSeparator></html:menuSeparator>
				<html:menuItem target="content" url="/import/form/${ nginx.id }" icon="copy" label="{import.nginx.conf}"></html:menuItem>
				<html:menuItem target="content" url="/report/search/${ nginx.id }" icon="object-align-bottom" label="{reports}" ></html:menuItem>
				<html:menuItem target="content" url="/sslCertificate/list/${ nginx.id }" icon="lock" label="{ssl.certificates}" ></html:menuItem>
				<html:menuItem target="content" url="/server/list/${ nginx.id }" icon="list" label="{servers}" ></html:menuItem>
				<html:menuItem target="content" url="/upstream/list/${ nginx.id }" icon="indent-left" label="{upstreams}"></html:menuItem>
				<html:menuItem target="content" url="/virtualHost/list/${ nginx.id }" icon="globe" label="{virtual.hosts}"></html:menuItem>
				<html:menuItem target="content" url="/accessLog/list/${ nginx.id }" icon="stats" label="{access.logs}"></html:menuItem>
				<html:menuItem target="content" url="/errorLog/list/${ nginx.id }" icon="fire" label="{error.logs}"></html:menuItem>
			</html:menuButton>
			<html:menuButton icon="equalizer" align="right">
				<html:menuItem url="/nginx/list" target="conteudo" label="{nginx.agent.empty}" rendered="${ empty(nginxList) }"></html:menuItem>
				<c:forEach items="${ nginxList }" var="nginx">
					<html:menuItem url="/applySessionFor/${ nginx.id }" icon="stop">
						<fmt:message key="nginx.agent.apply.session">
							<fmt:param value="${ nginx.name }"></fmt:param>
							<fmt:param value="${ nginx.endpoint }"></fmt:param>
						</fmt:message>
					</html:menuItem>
				</c:forEach>
			</html:menuButton>
		</html:container>
	</html:nav>
	<html:container>
		<html:alert state="warning" rendered="${ nginx == null }" label="{nginx.agent.no.session}"/>
		<html:alert state="info" rendered="${ nginx != null }">
			<fmt:message key="nginx.agent.session">
				<fmt:param>
					${ nginx.name } (${ nginx.endpoint })
				</fmt:param>
			</fmt:message>
		</html:alert>
	</html:container>

	<html:container>
		<html:block>
			<html:iframe url="${ nginx == null ? '/welcome' : x:concat('/dashboard/index/',nginx.id) }" name="content"></html:iframe>
		</html:block>
	</html:container>
	
	<ajax:function url="/version" name="version" executeOnDocumentLoad="true">
		<ajax:onSuccess>
			<ajax:target type="html" data="version" target="version"></ajax:target>
		</ajax:onSuccess>
	</ajax:function>
	
</html:view>