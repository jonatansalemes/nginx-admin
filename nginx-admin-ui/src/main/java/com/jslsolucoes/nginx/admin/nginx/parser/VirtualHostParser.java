package com.jslsolucoes.nginx.admin.nginx.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.ServerDirective;

public class VirtualHostParser implements Parser {

	private File file;

	public VirtualHostParser(File file) {
		this.file = file;
	}

	public List<Directive> parse() throws IOException {
		List<Directive> virtualHosts = new ArrayList<Directive>();
		for (String block : blocks(file)) {

			ServerDirective virtualHost = new ServerDirective();

			Matcher listen = Pattern.compile("listen(\\s{1,})([0-9]{2,})(\\s(.*?))?;").matcher(block);
			while (listen.find()) {
				virtualHost.setPort(Integer.valueOf(listen.group(2)));
			}

			Matcher serverName = Pattern.compile("server_name(\\s{1,})(.*?);").matcher(block);
			while (serverName.find()) {
				virtualHost.setAliases(Arrays.asList(serverName.group(2).split(" ")));
			}

			Matcher sslCertificate = Pattern.compile("ssl_certificate(\\s{1,})(.*?);").matcher(block);
			while (sslCertificate.find()) {
				virtualHost.setSslCertificate(sslCertificate.group(2));
			}

			Matcher sslCertificateKey = Pattern.compile("ssl_certificate_key(\\s{1,})(.*?);").matcher(block);
			while (sslCertificateKey.find()) {
				virtualHost.setSslCertificateKey(sslCertificateKey.group(2));
			}

			virtualHosts.add(virtualHost);
		}
		return virtualHosts;
	}

	private List<String> blocks(File file) throws IOException {
		List<String> blocks = new ArrayList<String>();
		List<String> lines = FileUtils.readLines(file, "UTF-8");
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
				if (atomicInteger.get() == 0) {
					blocks.add(StringUtils.join(lines.subList(indexStart, currentLine.get()), "\n"));
				}
			}
			currentLine.getAndIncrement();
		}
		return blocks;
	}

	@Override
	public Boolean accepts() throws IOException {
		return Pattern.compile("server(\\s{1,})\\{").matcher(content()).find();
	}

	private String content() throws IOException {
		return FileUtils.readFileToString(file, "UTF-8");
	}
}
