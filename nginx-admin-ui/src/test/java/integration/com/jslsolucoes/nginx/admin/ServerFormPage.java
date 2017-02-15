package integration.com.jslsolucoes.nginx.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ServerFormPage {

	private WebDriver driver;

	public ServerFormPage(WebDriver driver) {
		this.driver = driver;
		driver.get("http://localhost:8081/nginx-admin-ui/server/form");
	}
	
	public ServerFormPage fillIpWith(String ip){
		driver.findElement(By.name("ip")).sendKeys(ip);
		return this;
	}
	
	public ServerFormPage submit(){
		driver.findElement(By.cssSelector("a[data-type=submit]")).click();
		return new ServerFormPage(driver);
	}	
	
	public ServerListPage backToList(){
		return new ServerListPage(driver);
	}

}
