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
package com.jslsolucoes.nginx.admin.nginx.parser.directive;

import java.util.List;

public class UpstreamDirective implements Directive {

	private String name;
	private String strategy;
	private List<UpstreamDirectiveServer> servers;

	public UpstreamDirective(String name, String strategy, List<UpstreamDirectiveServer> servers) {
		this.name = name;
		this.strategy = strategy;
		this.servers = servers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public List<UpstreamDirectiveServer> getServers() {
		return servers;
	}

	public void setServers(List<UpstreamDirectiveServer> servers) {
		this.servers = servers;
	}

	@Override
	public DirectiveType type() {
		return DirectiveType.UPSTREAM;
	}
}
