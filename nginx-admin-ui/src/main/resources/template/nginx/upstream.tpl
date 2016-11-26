upstream ${ upstream.name } {
	${ upstream.strategy.name };
	<#list upstream.servers as server>
	server ${ server.ip }:${ server.port };
	</#list>
}