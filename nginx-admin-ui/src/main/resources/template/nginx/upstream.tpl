upstream ${ upstream.name } {
	${ upstream.strategy.name };
	<#list upstream.servers as upstreamServer>
	server ${ upstreamServer.server.ip }:${ upstreamServer.port?c };
	</#list>
}