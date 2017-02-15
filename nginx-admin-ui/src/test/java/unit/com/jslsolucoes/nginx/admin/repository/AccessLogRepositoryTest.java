package unit.com.jslsolucoes.nginx.admin.repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.jslsolucoes.nginx.admin.model.AccessLog;
import com.jslsolucoes.nginx.admin.repository.AccessLogRepository;
import com.jslsolucoes.nginx.admin.repository.impl.AccessLogRepositoryImpl;

import unit.com.jslsolucoes.nginx.admin.hibernate.Hibernate;


public class AccessLogRepositoryTest {
	private Hibernate hibernate;
	private AccessLogRepository accessLogRepository;
	

	@Before
	public void setUp() throws SQLException {
		MockitoAnnotations.initMocks(this);
		hibernate = Hibernate
				.build()
				.buildSessionFactory()
				.openSession()
				.beginTransaction();
		accessLogRepository = new AccessLogRepositoryImpl(hibernate.session());
	}
	
	@Test
	public void testLog(){
		AccessLog accessLog = new AccessLog();
		accessLog.setBodyBytesSent(1L);
		accessLog.setTimestamp(new Date());
		accessLog.setRemoteAddress("127.0.0.1");
		accessLog.setBytesSent(1L);
		accessLog.setConnection(1L);
		accessLog.setConnectionRequest(1L);
		accessLog.setHttpReferrer("-");
		accessLog.setMsec(BigDecimal.ZERO);
		accessLog.setRequest("request");
		accessLog.setStatus(200);
		accessLog.setScheme("http");
		accessLog.setRequestLength(1L);
		accessLog.setRequestTime(BigDecimal.ZERO);
		accessLog.setRequestMethod("get");
		accessLog.setRequestUri("/version");
		accessLog.setServerName("localhost");
		accessLog.setServerPort(80);
		accessLog.setServerProtocol("http");
		accessLog.setHttpUserAgent("-");
		accessLog.setHttpXForwardedFor("-");
		accessLogRepository.log(accessLog);
		Assert.assertTrue(accessLog.getId() > 0);
	}
	
	@After
	public void tearDown() throws Exception {
		hibernate
		.rollback()
		.close();
	}
}
