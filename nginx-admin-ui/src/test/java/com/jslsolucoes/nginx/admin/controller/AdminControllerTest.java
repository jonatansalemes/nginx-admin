package com.jslsolucoes.nginx.admin.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.jslsolucoes.nginx.admin.nginx.detail.NginxDetail;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResult;

import br.com.caelum.vraptor.util.test.MockResult;

public class AdminControllerTest {

	private AdminController controller;
	private MockResult result;
	private RuntimeResult runtimeResult;
	
	@Mock private Runner runner;
	@Mock private NginxDetail nginxDetail;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		controller = new AdminController(result, runner, nginxDetail);
	}
	
	@Test
	public void testDashboard() {
		controller.dashboard();
		Assert.assertNotNull(result.included().get("so"));
		Assert.assertNotNull(result.included().get("nginxDetail"));
	}
	
	@Test
	public void testConfigure() {
		controller.configure();
	}
	
	@Test
	public void testStop() {
		Mockito.when(runner.reload()).thenReturn(runtimeResult);
		controller.stop();
		Assert.assertEquals(runtimeResult,result.included().get("runtimeResult"));
	}
	
	@Test
	public void testReload() {
		Mockito.when(runner.reload()).thenReturn(runtimeResult);
		controller.reload();
		Assert.assertEquals(runtimeResult,result.included().get("runtimeResult"));
	}
	
	@Test
	public void testStart() {
		Mockito.when(runner.reload()).thenReturn(runtimeResult);
		controller.start();
		Assert.assertEquals(runtimeResult,result.included().get("runtimeResult"));
	}
	
	@Test
	public void testStatus() {
		Mockito.when(runner.reload()).thenReturn(runtimeResult);
		controller.status();
		Assert.assertEquals(runtimeResult,result.included().get("runtimeResult"));
	}
	
	@Test
	public void testRestart() {
		Mockito.when(runner.reload()).thenReturn(runtimeResult);
		controller.restart();
		Assert.assertEquals(runtimeResult,result.included().get("runtimeResult"));
	}
	
	@Test
	public void testConfig() {
		Mockito.when(runner.reload()).thenReturn(runtimeResult);
		controller.testConfig();
		Assert.assertEquals(runtimeResult,result.included().get("runtimeResult"));
	}
	
	
	
	

		
}