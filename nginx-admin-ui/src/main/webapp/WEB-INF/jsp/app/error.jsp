<%@ page isErrorPage="true" import="java.io.*"%>
<%@include file="taglibs.jsp"%>
<c:set var="exception">
<%
	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	exception.printStackTrace(printWriter);
	out.println(stringWriter);
	printWriter.close();
	stringWriter.close();
%>
</c:set>
<html:view>
	<html:alert state="danger" label="{error}">
		${ exception }
	</html:alert>
</html:view>