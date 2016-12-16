package com.jslsolucoes.nginx.admin.nginx.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.UpstreamDirective;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.UpstreamDirectiveServer;

public class UpstreamParser implements Parser {

	private File file;

	public UpstreamParser(File file) {
		this.file = file;
	}

	public List<Directive> parse() throws IOException {
		List<Directive> upstreams = new ArrayList<Directive>();
		Matcher upstreamers = Pattern.compile("upstream(\\s{1,})(.*?)\\{(.*?)\\}", Pattern.DOTALL)
				.matcher(content());
		while (upstreamers.find()) {
			String name = upstreamers.group(2).trim();
			String body = upstreamers.group(3);
			upstreams.add(new UpstreamDirective(name, strategy(body), servers(body)));
		}
		return upstreams;
	}

	private List<UpstreamDirectiveServer> servers(String body) {
		List<UpstreamDirectiveServer> servers = new ArrayList<UpstreamDirectiveServer>();
		Matcher ips = Pattern.compile("server(\\s{1,})(.*?)(:([0-9]{2,}))?(\\s(.*?))?;").matcher(body);
		while (ips.find()) {
			UpstreamDirectiveServer server = new UpstreamDirectiveServer();
			server.setIp(ips.group(2).trim());
			if (!StringUtils.isEmpty(ips.group(4))) {
				server.setPort(Integer.valueOf(ips.group(4).trim()));
			}
			servers.add(server);
		}
		return servers;
	}

	private String strategy(String body) {
		String strategy = "ip_hash";
		if (Pattern.compile("least_conn;").matcher(body).find()) {
			strategy = "least_conn";
		}
		if (Pattern.compile("round_robin;").matcher(body).find()) {
			strategy = "round_robin";
		}
		return strategy;
	}
	
	private String content() throws IOException{
		return FileUtils.readFileToString(file, "UTF-8");
	}

	@Override
	public Boolean accepts() throws IOException {
		return Pattern.compile("upstream(\\s{1,})(.*?)\\{(.*?)\\}", Pattern.DOTALL)
				.matcher(content()).find();
	}
}
