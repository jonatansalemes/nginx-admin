package com.jslsolucoes.nginx.admin.nginx.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.LocationDirective;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.VirtualHostDirective;

public class ServerParser implements Parser {

	private String fileContent;

	public ServerParser(String fileContent) {
		this.fileContent = fileContent;
	}

	@Override
	public List<Directive> parse() {
		List<Directive> virtualHosts = new ArrayList<>();
		for (String block : blocks()) {

			VirtualHostDirective virtualHost = new VirtualHostDirective();

			Matcher listen = Pattern.compile("listen(\\s{1,})([0-9]{2,})(\\s(.*?))?;").matcher(block);
			if (listen.find()) {
				virtualHost.setPort(Integer.valueOf(listen.group(2)));
			}

			Matcher serverName = Pattern.compile("server_name(\\s{1,})(.*?);").matcher(block);
			if (serverName.find()) {
				virtualHost.setAliases(Arrays.asList(serverName.group(2).split(" ")));
			}

			Matcher sslCertificate = Pattern.compile("ssl_certificate(\\s{1,})(.*?);").matcher(block);
			if (sslCertificate.find()) {
				virtualHost.setSslCertificate(sslCertificate.group(2));
			}

			Matcher sslCertificateKey = Pattern.compile("ssl_certificate_key(\\s{1,})(.*?);").matcher(block);
			if (sslCertificateKey.find()) {
				virtualHost.setSslCertificateKey(sslCertificateKey.group(2));
			}

			virtualHost.setLocations(locations(block));
			virtualHosts.add(virtualHost);
		}
		return virtualHosts;
	}

	private List<LocationDirective> locations(String block) {
		List<LocationDirective> locationDirectives = new ArrayList<>();

		Matcher locations = Pattern.compile("location(\\s{1,})(.*?)(\\s{0,})\\{(.*?)\\}", Pattern.DOTALL)
				.matcher(block);
		while (locations.find()) {
			LocationDirective locationDirective = new LocationDirective();
			locationDirective.setPath(locations.group(2));

			Matcher upstream = Pattern.compile("proxy_pass(\\s{1,})(https?:\\/\\/)(.*?);").matcher(locations.group(4));
			while (upstream.find()) {
				locationDirective.setUpstream(upstream.group(3));
			}
			locationDirectives.add(locationDirective);
		}
		return locationDirectives;
	}

	private List<String> blocks() {
		List<String> blocks = new ArrayList<>();
		List<String> lines = Arrays.asList(fileContent.split("\n"));

		AtomicInteger atomicInteger = new AtomicInteger(0);
		AtomicInteger currentLine = new AtomicInteger(1);
		Integer indexStart = 0;
		for (String line : lines) {
			if (line.contains("{")) {
				atomicInteger.getAndIncrement();
				if (line.contains("server")) {
					indexStart = currentLine.get() - 1;
				}
			} else if (line.contains("}")) {
				atomicInteger.getAndDecrement();
				if (atomicInteger.get() == 0 && lines.get(indexStart).matches("server(\\s{1,})\\{")) {
					blocks.add(StringUtils.join(lines.subList(indexStart, currentLine.get()), "\n"));
				}
			}
			currentLine.getAndIncrement();
		}
		return blocks;
	}

	@Override
	public Boolean accepts() {
		return Pattern.compile("server(\\s{1,})\\{").matcher(fileContent).find();
	}

}
