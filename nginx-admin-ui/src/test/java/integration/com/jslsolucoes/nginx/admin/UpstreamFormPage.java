package integration.com.jslsolucoes.nginx.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UpstreamFormPage {

	private WebDriver driver;

	public UpstreamFormPage(WebDriver driver) {
		this.driver = driver;
		driver.get("http://localhost:8081/nginx-admin-ui/upstream/form");
	}
	
	public UpstreamFormPage fillUpstreamNameWith(String name){
		driver.findElement(By.name("name")).sendKeys(name);
		return this;
	}
	
	public UpstreamFormPage submit(){
		driver.findElement(By.cssSelector("a[data-type=submit]")).click();
		return new UpstreamFormPage(driver);
	}	
	
	public UpstreamListPage backToList(){
		return new UpstreamListPage(driver);
	}
}
