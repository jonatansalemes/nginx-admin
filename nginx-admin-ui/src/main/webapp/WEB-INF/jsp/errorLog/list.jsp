<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:panel>
		<html:panelHead label="{error.log.content}"></html:panelHead>
		<html:panelBody>
			<html:alert state="danger">
				${ x:lineBreak(errorLogContent) }
			</html:alert>
		</html:panelBody>
	</html:panel>

</html:view>