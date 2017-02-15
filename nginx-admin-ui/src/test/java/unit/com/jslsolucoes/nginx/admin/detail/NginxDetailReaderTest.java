package unit.com.jslsolucoes.nginx.admin.detail;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.nginx.detail.NginxDetail;
import com.jslsolucoes.nginx.admin.nginx.detail.NginxDetailReader;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResult;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResultType;


public class NginxDetailReaderTest {
	
	
	private NginxDetailReader nginxDetailReader;
	
	@Mock private Runner runner;
	@Mock private Nginx nginx;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		nginxDetailReader = new NginxDetailReader(runner, nginx);
	}
	
	@Test
	public void testDetail() throws IOException{
		File random = new File(FileUtils.getTempDirectory(),UUID.randomUUID().toString()+".xml");
		FileUtils.writeStringToFile(random, "12345", "UTF-8");
		Mockito.when(nginx.pid()).thenReturn(random);
		Mockito.when(runner.version()).thenReturn(new RuntimeResult(RuntimeResultType.SUCCESS, "1.12.1"));
		NginxDetail nginxDetail = nginxDetailReader.details();
		Assert.assertEquals(Integer.valueOf(12345),nginxDetail.getPid());
		Assert.assertEquals("1.12.1",nginxDetail.getVersion());
		Assert.assertTrue(nginxDetail.getUptime().compareTo(BigDecimal.ZERO) >= 0);
		Assert.assertNotNull(nginxDetail.getAddress());
		FileUtils.forceDelete(random);
	}

}
