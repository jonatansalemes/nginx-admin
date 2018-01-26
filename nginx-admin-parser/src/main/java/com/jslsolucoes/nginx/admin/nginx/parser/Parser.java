package com.jslsolucoes.nginx.admin.nginx.parser;

import java.util.List;

import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;

public interface Parser {
	public Boolean accepts();

	public List<Directive> parse();
}
