package unit.com.jslsolucoes.nginx.admin.os;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import org.junit.Assert;
import org.junit.Test;

import com.jslsolucoes.nginx.admin.os.OperationalSystem;
import com.jslsolucoes.nginx.admin.os.OperationalSystemInfo;

public class OperationalSystemTest {

	
	@Test
	public void soDetection(){
		OperationalSystemInfo operationalSystemInfo = OperationalSystem.info();
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		Assert.assertEquals(operationalSystemInfo.getArch(), operatingSystemMXBean.getArch());
		Assert.assertEquals(operationalSystemInfo.getName(), operatingSystemMXBean.getName());
		Assert.assertEquals(operationalSystemInfo.getVersion(), operatingSystemMXBean.getVersion());
	}
}
