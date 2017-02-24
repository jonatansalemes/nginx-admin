package unit.com.jslsolucoes.nginx.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.jslsolucoes.nginx.admin.controller.ServerController;
import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.repository.ServerRepository;

import br.com.caelum.vraptor.util.test.MockResult;

public class ServerControllerTest {

	private ServerController serverController;
	
	private MockResult mockResult;
	
	@Mock
	private ServerRepository serverRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockResult = new MockResult();
		serverController = new ServerController(mockResult, serverRepository);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void list() {
		List<Server> servers = new ArrayList<Server>();
		servers.add(new Server(Long.valueOf(1), "127.0.0.1"));
		Mockito.when(serverRepository.listAll()).thenReturn(servers);
		serverController.list();
		Assert.assertTrue(((List<Server>) mockResult.included().get("serverList")).size() == 1);	
	}
	
	
	@Test
	public void edit() {
		Server server = new Server(Long.valueOf(1), "127.0.0.1");
		Mockito.when(serverRepository.load(Mockito.any())).thenReturn(server);
		serverController.edit(Long.valueOf(1));
		Assert.assertEquals(server.getIp(),((Server) mockResult.included().get("server")).getIp());	
	}
}
