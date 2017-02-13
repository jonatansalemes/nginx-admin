package com.jslsolucoes.nginx.admin.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jslsolucoes.nginx.admin.hibernate.Hibernate;
import com.jslsolucoes.nginx.admin.model.User;
import com.jslsolucoes.nginx.admin.repository.impl.UserRepositoryImpl;

public class UserRepositoryTest {

	private Hibernate hibernate;
	private UserRepository userRepository;

	@Mock
	private MailRepository mailRepository;

	@Before
	public void setUp() throws SQLException {
		MockitoAnnotations.initMocks(this);
		hibernate = Hibernate
				.build()
				.buildSessionFactory()
				.openSession()
				.beginTransaction();
		userRepository = new UserRepositoryImpl(hibernate.session(), mailRepository);
	}
	
	@Test
	public void changePassword(){
		userRepository.createAdministrator("aa@aa.com.br", "12345678");
		User user = userRepository.authenticate(new User("aa@aa.com.br", "12345678"));
		assertEquals(3, userRepository.validateBeforeChangePassword(user, "123456", "123456", "12348").size());
		assertEquals(0, userRepository.validateBeforeChangePassword(user, "12345678", "123456789", "123456789").size());
		userRepository.changePassword(user, "123456789");
		assertEquals(DigestUtils.sha256Hex("123456789"),userRepository.load(user).getPassword());
	}
	
	
	@Test
	public void createAdministrator() {
		userRepository.createAdministrator("aa@aa.com.br", "12345678");
		List<User> users = userRepository.listAll();
		assertEquals(1, users.size());
		assertEquals("aa@aa.com.br", users.get(0).getLogin());
	}

	@Test
	public void shouldNotAuthenticate() {
		userRepository.createAdministrator("aa@aa.com.br", "12345678");
		User user = userRepository.authenticate(new User("aa@aa.com.br", "xxxxxxx"));
		assertNull(user);
	}

	@Test
	public void shouldAuthenticate() {
		userRepository.createAdministrator("aa@aa.com", "123456");
		User user = userRepository.authenticate(new User("aa@aa.com", "123456"));
		assertEquals("aa@aa.com", user.getLogin());
	}

	@After
	public void tearDown() throws Exception {
		hibernate
		.rollback()
		.close();
	}

}
