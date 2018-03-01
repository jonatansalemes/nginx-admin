upstream ${ name } {
    <#if strategy??>${ strategy };</#if>
	<#list endpoints as endpoint>
	server ${ endpoint.ip }:${ endpoint.port?c };
	</#list> 
}