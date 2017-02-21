package integration.com.jslsolucoes.nginx.admin.page;

import org.openqa.selenium.WebDriver;

public class ServerListPage {

	private WebDriver driver;

	public ServerListPage(WebDriver driver) {
		this.driver = driver;
		driver.get("http://localhost:8081/nginx-admin-ui/server/list");
	}
	
	public ServerFormPage callForm(){
		return new ServerFormPage(driver);
	}

	public HomePage backToHome() {
		return new HomePage(driver);
	}
}
