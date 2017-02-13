package com.jslsolucoes.nginx.admin.template;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;

public class TemplateProcessorTest {

	private File random;
	private TemplateProcessor templateProcessor;
	
	
	@Before
	public void setUp() {
		random = new File(FileUtils.getTempDirectory(),UUID.randomUUID().toString()+".xml");
		templateProcessor = TemplateProcessor.build().withTemplateFolder("/template");
	}
	
	@Test
	public void testCreatingOfFile() throws NginxAdminException, IOException {
		templateProcessor
		.withData("param1","fds")
		.withData("param2","fds")
		.withData("param3","fds")
		.withTemplate("test.tpl")
		.toLocation(random)
		.process();
		Assert.assertTrue(random.exists());
	}
	
	
	@Test
	public void testFillContent() throws NginxAdminException, IOException {
		String value1 = RandomStringUtils.randomAlphanumeric(10);
		String value2 = RandomStringUtils.randomAlphanumeric(10);
		String value3 = RandomStringUtils.randomAlphanumeric(10);
		templateProcessor
		.withData("param1",value1)
		.withData("param2",value2)
		.withData("param3",value3)
		.withTemplate("test.tpl")
		.toLocation(random)
		.process();
		Assert.assertEquals(value1.concat(value2).concat(value3), FileUtils.readFileToString(random, "utf-8"));
	}
	
	@After
	public void tearDown() throws IOException {
		FileUtils.forceDelete(random);
	}
}
