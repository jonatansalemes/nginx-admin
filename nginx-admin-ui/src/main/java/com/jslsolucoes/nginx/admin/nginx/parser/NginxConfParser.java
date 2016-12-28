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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;

import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;

public class NginxConfParser {
	
	
	private String location;

	public NginxConfParser(String location) {
		this.location = location;
	}

	public List<Directive> parse() throws IOException {
		List<Directive> directives = new ArrayList<Directive>();
		Matcher includes = Pattern.compile("include (.*)/(.*);").matcher(content(new File(location)));
		while (includes.find()) {
			String directory = includes.group(1).trim();
			File include = new File(directory);
			if(include.exists()){
				String pattern = includes.group(2).trim().replaceAll("\\*", "\\.\\*");
				for (File file : FileUtils.listFiles(include, new RegexFileFilter(pattern), null)) {
					for(Parser parser : parsers(content(file))){
						if(parser.accepts()){
							directives.addAll(parser.parse());
						}
					}
				}
			}
		}
		return directives;
	}
	
	@SuppressWarnings("serial")
	private List<Parser> parsers(String fileContent){
		return new ArrayList<Parser>(){{
			add(new UpstreamParser(fileContent));
			add(new ServerParser(fileContent));
		}};
	}
	
	private String content(File file) throws IOException {
		return FileUtils.readFileToString(file, "UTF-8").replaceAll("#(.*)","");
	}
}
