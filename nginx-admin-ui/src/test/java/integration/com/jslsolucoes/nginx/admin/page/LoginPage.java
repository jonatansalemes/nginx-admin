package integration.com.jslsolucoes.nginx.admin.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

	
	private WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		driver.get("http://localhost:8081/nginx-admin-ui/user/login");
	}
	
	public LoginPage fillLoginWith(String login){
		driver.findElement(By.name("login")).sendKeys(login);
		return this;
	}
	
	public LoginPage fillPasswordWith(String password){
		driver.findElement(By.name("password")).sendKeys(password);
		return this;
	}
	
	public HomePage submit(){
		driver.findElement(By.cssSelector("a[data-type=submit]")).click();
		return new HomePage(driver);
	}
	
}
