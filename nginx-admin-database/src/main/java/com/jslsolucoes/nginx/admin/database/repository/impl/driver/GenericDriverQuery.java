package com.jslsolucoes.nginx.admin.database.repository.impl.driver;

import java.io.IOException;
import java.io.StringWriter;

import com.jslsolucoes.template.TemplateProcessor;

public class GenericDriverQuery implements DriverQuery {

	
	private String folder;

	public GenericDriverQuery(String folder) {
		this.folder = folder;
	}
	
	@Override
	public String exists(String database, String table) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateProcessor.newBuilder().withData("database", database).withData("table", table)
					.withTemplate(folder, "exists.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

	@Override
	public String create(String database, String table) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateProcessor.newBuilder().withData("database", database)
					.withData("table", table)
					.withTemplate(folder, "create.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

	@Override
	public String current(String database, String table) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateProcessor.newBuilder().withData("database", database).withData("table", table)
					.withTemplate(folder, "current.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

	@Override
	public String insert(String database, String table) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateProcessor.newBuilder().withData("database", database).withData("table", table)
					.withTemplate(folder, "insert.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

}
