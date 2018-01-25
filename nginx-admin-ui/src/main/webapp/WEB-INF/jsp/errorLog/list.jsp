<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:grid data="${ errorLogList }" var="errorLog" label="{error.log.list}" 
	 	totalResults="${ totalResults }" url="/errorLog/list">
		<html:gridColumn label="{error.log.timestamp}" exportable="true" format="timestamp">
				${ errorLog.timestamp }
		</html:gridColumn>
		<html:gridColumn label="{error.log.level}" exportable="true">
				${ errorLog.level }
		</html:gridColumn>
		<html:gridColumn label="{error.log.message}" exportable="true" collapsable="true">
				${ errorLog.message }
		</html:gridColumn>
		<html:gridColumn label="{error.log.pid}" exportable="true">
				${ errorLog.pid }
		</html:gridColumn>
		<html:gridColumn label="{error.log.cid}" exportable="true">
				${ errorLog.cid }
		</html:gridColumn>
		<html:gridColumn label="{error.log.tid}" exportable="true">
				${ errorLog.tid }
		</html:gridColumn>
	
	</html:grid>

</html:view>