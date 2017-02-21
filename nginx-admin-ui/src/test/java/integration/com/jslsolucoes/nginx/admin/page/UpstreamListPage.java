package integration.com.jslsolucoes.nginx.admin.page;

import org.openqa.selenium.WebDriver;

public class UpstreamListPage {

	private WebDriver driver;

	public UpstreamListPage(WebDriver driver) {
		this.driver = driver;
		driver.get("http://localhost:8081/nginx-admin-ui/upstream/list");
	}
	
	public UpstreamFormPage callForm(){
		return new UpstreamFormPage(driver);
	}

	public HomePage backToHome() {
		return new HomePage(driver);
	}

}
