package com.jslsolucoes.nginx.admin.nginx.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;

public class NginxConfParser {

	private String location;

	public NginxConfParser(String location) {
		this.location = location;
	}

	public List<Directive> parse() throws IOException {
		List<Directive> directives = new ArrayList<>();
		Matcher includes = Pattern.compile("include (.*)/(.*);").matcher(content(new File(location)));
		while (includes.find()) {
			String directory = includes.group(1).trim();
			File include = new File(directory);
			if (include.exists()) {
				String pattern = includes.group(2).trim().replaceAll("\\*", "\\.\\*");
				for (File file : FileUtils.listFiles(include, new RegexFileFilter(pattern), TrueFileFilter.TRUE)) {
					for (Parser parser : parsers(content(file))) {
						if (parser.accepts()) {
							directives.addAll(parser.parse());
						}
					}
				}
			}
		}
		return directives;
	}

	private List<Parser> parsers(String fileContent) {
		return Arrays.asList(new UpstreamParser(fileContent), new ServerParser(fileContent));
	}

	private String content(File file) throws IOException {
		return FileUtils.readFileToString(file, "UTF-8").replaceAll("#(.*)", "");
	}
}
