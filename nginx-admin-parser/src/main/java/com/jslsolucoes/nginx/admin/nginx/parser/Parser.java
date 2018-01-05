package com.jslsolucoes.nginx.admin.nginx.parser;

import java.io.IOException;
import java.util.List;

import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;

public interface Parser {
	public Boolean accepts() throws IOException;

	public List<Directive> parse() throws IOException;
}
