package com.jslsolucoes.nginx.admin.database.repository.impl.driver;

import java.io.IOException;
import java.io.StringWriter;

import com.jslsolucoes.template.TemplateProcessor;

public class H2DriverQuery implements DriverQuery {
	
	@Override
	public String exists(String schema, String tableName) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateProcessor.newBuilder().withData("schema", schema).withData("tableName", tableName)
					.withTemplate("/db/h2", "exists.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

	@Override
	public String create(String schema, String tableName) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateProcessor.newBuilder().withData("schema", schema).withData("tableName", tableName)
					.withTemplate("/db/h2", "create.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

	@Override
	public String current(String schema, String tableName) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateProcessor.newBuilder().withData("schema", schema).withData("tableName", tableName)
					.withTemplate("/db/h2", "current.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

	@Override
	public String insert(String schema, String tableName) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateProcessor.newBuilder().withData("schema", schema).withData("tableName", tableName)
					.withTemplate("/db/h2", "insert.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}
}
