package integration.com.jslsolucoes.nginx.admin;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class IntegrationTestCase {

	@Test
	public void testBasic() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "/Volumes/hd2/softwares/chrome-driver/chromedriver");
		WebDriver driver = new ChromeDriver();
		
			new LoginPage(driver)
			.fillLoginWith("aa@localhost.com")
			.fillPasswordWith("12345678")
			.submit()
			.callServerList()
			.callForm()
			.fillIpWith("192.168.0.1")
			.submit()
			.backToList()
			.backToHome()
			.callUpstreamList()
			.callForm()
			.fillUpstreamNameWith("example1")
			.submit()
			.backToList()
			.backToHome();
	}
}