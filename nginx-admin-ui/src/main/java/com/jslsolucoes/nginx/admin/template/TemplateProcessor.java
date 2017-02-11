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
package com.jslsolucoes.nginx.admin.template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
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

	public void process() throws IOException, TemplateException {
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
