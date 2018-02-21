package com.jslsolucoes.nginx.admin.database.repository.impl.driver;

import java.io.IOException;
import java.io.StringWriter;

import com.jslsolucoes.template.TemplateBuilder;

public class GenericDriverQuery implements DriverQuery {

	
	private String folder;

	public GenericDriverQuery(String folder) {
		this.folder = folder;
	}
	
	@Override
	public String exists(String database, String table) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateBuilder.newBuilder().withData("database", database).withData("table", table)
					.withClasspathTemplate(folder, "exists.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

	@Override
	public String create(String database, String table) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateBuilder.newBuilder().withData("database", database)
					.withData("table", table)
					.withClasspathTemplate(folder, "create.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

	@Override
	public String current(String database, String table) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateBuilder.newBuilder().withData("database", database).withData("table", table)
					.withClasspathTemplate(folder, "current.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

	@Override
	public String insert(String database, String table) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateBuilder.newBuilder().withData("database", database).withData("table", table)
					.withClasspathTemplate(folder, "insert.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

	@Override
	public String init(String database) {
		try (StringWriter stringWriter = new StringWriter()) {
			TemplateBuilder.newBuilder().withData("database", database)
					.withClasspathTemplate(folder, "init.tpl").withEncoding("UTF-8").withOutput(stringWriter).process();
			return stringWriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

}
