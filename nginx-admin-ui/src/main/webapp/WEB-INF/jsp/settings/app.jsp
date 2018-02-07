<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">
	
	<html:panel>
			<html:panelHead label="{application.settings}"></html:panelHead>
			<html:panelBody>
				<html:table>
					<html:tableLine>
						<html:tableColumn header="true" label="{application.version}"></html:tableColumn>
						<html:tableColumn>${ application.version }</html:tableColumn>
					</html:tableLine>
					<html:tableLine>
						<html:tableColumn header="true" label="{application.url.base}"></html:tableColumn>
						<html:tableColumn>${ application.urlBase }</html:tableColumn>
					</html:tableLine>
				</html:table>
			</html:panelBody>
		</html:panel>
	
</html:view>