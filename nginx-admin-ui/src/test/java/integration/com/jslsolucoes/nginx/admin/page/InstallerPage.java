package integration.com.jslsolucoes.nginx.admin.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InstallerPage {

	
	private WebDriver driver;

	public InstallerPage(WebDriver driver) {
		this.driver = driver;
		driver.get("http://localhost:8081/nginx-admin-ui/installer/form");
	}
	
	public InstallerPage fillAdminEmailWith(String email){
		driver.findElement(By.name("login")).sendKeys(email);
		return this;
	}
	
	public InstallerPage fillAdminEmailConfirmWith(String email){
		driver.findElement(By.name("loginConfirm")).sendKeys(email);
		return this;
	}
	
	public InstallerPage fillAdminPasswordWith(String password){
		driver.findElement(By.name("adminPassword")).sendKeys(password);
		return this;
	}
	
	public InstallerPage fillAdminPasswordConfirmWith(String password){
		driver.findElement(By.name("adminPasswordConfirm")).sendKeys(password);
		return this;
	}
	
	public InstallerPage fillNginxBinaryPathWith(String nginxBinPath){
		WebElement nginxBin = driver.findElement(By.name("nginxBin"));
		nginxBin.clear();
		nginxBin.sendKeys(nginxBinPath);
		return this;
	}
	
	public InstallerPage fillNginxSettingsPathWith(String settingsPath){
		WebElement nginxSettings = driver.findElement(By.name("nginxSettings"));
		nginxSettings.clear();
		nginxSettings.sendKeys(settingsPath);
		driver.findElement(By.cssSelector("a[data-type=submit]")).click();
		return this;
	}
	
	public LoginPage submit(){
		driver.findElement(By.cssSelector("a[data-type=submit]")).click();
		return new LoginPage(driver);
	}
}
