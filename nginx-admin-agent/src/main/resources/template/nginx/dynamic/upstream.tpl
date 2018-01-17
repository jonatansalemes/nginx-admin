upstream ${ name } {
	${ strategy };
	<#list endpoints as endpoint>
	server ${ endpoint.ip }:${ endpoint.port?c };
	</#list>
}