<%@include file="../app/taglibs.jsp"%>
<html:view>
	<html:row>
		<html:col size="3">
			<html:listGroup>
				<html:listGroupItem>
					<html:link url="/smtp/edit" target="settings"
						label="{smtp.settings}"></html:link>
				</html:listGroupItem>
			</html:listGroup>
		</html:col>
		<html:col size="9">
			<html:iframe url="/settings/welcome" name="settings"></html:iframe>
		</html:col>
	</html:row>

</html:view>