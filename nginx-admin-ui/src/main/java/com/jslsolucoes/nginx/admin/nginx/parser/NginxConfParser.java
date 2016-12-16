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
		Matcher includes = Pattern.compile("include (.*)/(.*);").matcher(FileUtils.readFileToString(new File(location), "UTF-8"));
		while (includes.find()) {
			String directory = includes.group(1).trim();
			String pattern = includes.group(2).trim().replaceAll("\\*", "\\.\\*");
			for (File file : FileUtils.listFiles(new File(directory), new RegexFileFilter(pattern), null)) {
				for(Parser parser : parsers(file)){
					if(parser.accepts()){
						directives.addAll(parser.parse());
					}
				}
			}
		}
		return directives;
	}
	
	@SuppressWarnings("serial")
	private List<Parser> parsers(File file){
		return new ArrayList<Parser>(){{
			add(new UpstreamParser(file));
			add(new VirtualHostParser(file));
		}};
	}
}
