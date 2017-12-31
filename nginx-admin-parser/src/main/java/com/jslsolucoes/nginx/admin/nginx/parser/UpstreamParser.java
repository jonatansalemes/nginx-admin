/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.nginx.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.UpstreamDirective;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.UpstreamDirectiveServer;

public class UpstreamParser implements Parser {

	private String fileContent;

	public UpstreamParser(String fileContent) {
		this.fileContent = fileContent;
	}

	@Override
	public List<Directive> parse() throws IOException {
		List<Directive> upstreams = new ArrayList<>();
		Matcher upstreamers = Pattern.compile("upstream(\\s{1,})(.*?)\\{(.*?)\\}", Pattern.DOTALL).matcher(fileContent);
		while (upstreamers.find()) {
			String name = upstreamers.group(2).trim();
			String body = upstreamers.group(3);
			upstreams.add(new UpstreamDirective(name, strategy(body), servers(body)));
		}
		return upstreams;
	}

	private List<UpstreamDirectiveServer> servers(String body) {
		List<UpstreamDirectiveServer> servers = new ArrayList<>();
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

	@Override
	public Boolean accepts() throws IOException {
		return Pattern.compile("upstream(\\s{1,})(.*?)\\{(.*?)\\}", Pattern.DOTALL).matcher(fileContent).find();
	}
}
