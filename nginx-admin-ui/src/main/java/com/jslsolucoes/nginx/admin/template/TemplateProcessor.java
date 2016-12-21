package com.jslsolucoes.nginx.admin.template;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class TemplateProcessor {

	private Map<String, Object> data = new HashMap<String, Object>();
	private String template;
	private File location;

	public TemplateProcessor withTemplate(String template) {
		this.template = template;
		return this;
	}

	public TemplateProcessor withData(String key, Object object) {
		data.put(key, object);
		return this;
	}

	public TemplateProcessor toLocation(File location) {
		this.location = location;
		return this;
	}

	public void process() throws Exception {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setTemplateLoader(new ClassTemplateLoader(TemplateProcessor.class, "/template/dynamic/nginx"));
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setLogTemplateExceptions(false);
		
		Template template = configuration.getTemplate(this.template);
		Writer writer = new StringWriter();
		template.process(data, writer);
		writer.close();
		FileUtils.writeStringToFile(location, writer.toString(), "UTF-8");
	}

}
