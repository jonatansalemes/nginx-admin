package integration.com.jslsolucoes.nginx.admin.page;

import org.openqa.selenium.WebDriver;

public class HomePage {

	private WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		driver.get("http://localhost:8081/nginx-admin-ui/");
	}
	
	public ServerListPage callServerList(){
		return new ServerListPage(driver);
	}

	public UpstreamListPage callUpstreamList() {
		return new UpstreamListPage(driver);
	}
}
