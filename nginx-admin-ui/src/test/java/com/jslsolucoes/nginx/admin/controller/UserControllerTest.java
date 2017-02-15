package com.jslsolucoes.nginx.admin.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jslsolucoes.nginx.admin.model.User;
import com.jslsolucoes.nginx.admin.repository.UserRepository;
import com.jslsolucoes.nginx.admin.session.UserSession;

import br.com.caelum.vraptor.util.test.MockResult;

public class UserControllerTest {

	private UserController controller;
	private MockResult result;
	private UserSession userSession;
	
	@Mock private UserRepository userRepository;
	@Mock private Properties properties;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		userSession = new UserSession();
		result = new MockResult();
		controller = new UserController(properties, userSession, result, userRepository);
	}
	
	@Test
	public void login() {
		when(properties.get("app.version")).thenReturn("x.x.x");
		controller.login();
		assertEquals("x.x.x",result.included().get("version"));
	}

	@Test
	public void shouldNotAuthenticate() {
		when(userRepository.authenticate(new User("aa","xx"))).thenReturn(null);
		controller.authenticate("aa", "xx");
		assertTrue((Boolean) result.included().get("invalid"));
	}
	
	@Test
	public void shouldAuthenticate() {
		User user = new User("aa","xx");
		user.setPasswordForceChange(0);
		when(userRepository.authenticate(any())).thenReturn(user);
		when(userRepository.loadForSession(any())).thenReturn(user);
		controller.authenticate("aa", "xx");
		Assert.assertEquals(userSession.getUser(),user);
	}
	
	@Test
	public void logout() {
		User user = new User();
		userSession.setUser(user);
		Assert.assertEquals(userSession.getUser(),user);
		controller.logout();
		Assert.assertNull(userSession.getUser());
	}
	
	
}