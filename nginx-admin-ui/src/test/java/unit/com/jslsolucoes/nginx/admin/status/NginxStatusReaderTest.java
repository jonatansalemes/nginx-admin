package unit.com.jslsolucoes.nginx.admin.status;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;
import com.jslsolucoes.nginx.admin.nginx.status.NginxStatus;
import com.jslsolucoes.nginx.admin.nginx.status.NginxStatusReader;

public class NginxStatusReaderTest {

	
	private NginxStatusReader nginxStatusReader;
	
	@Mock private Runner runner;
	@Mock private Nginx nginx;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		nginxStatusReader = new NginxStatusReader();
	}
	
	@Test
	public void testDetail() throws IOException{
		NginxStatus nginxStatus = nginxStatusReader.status();
		
		Assert.assertEquals(Integer.valueOf(0),nginxStatus.getAccepts());
		Assert.assertEquals(Integer.valueOf(0),nginxStatus.getActiveConnection());
		Assert.assertEquals(Integer.valueOf(0),nginxStatus.getHandled());
		Assert.assertEquals(Integer.valueOf(0),nginxStatus.getReading());
		Assert.assertEquals(Integer.valueOf(0),nginxStatus.getRequests());
		Assert.assertEquals(Integer.valueOf(0),nginxStatus.getWaiting());
		Assert.assertEquals(Integer.valueOf(0),nginxStatus.getWriting());
	}
	
}
