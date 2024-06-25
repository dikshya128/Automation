import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LogIn {
	private WebDriver driver;
	
	public LogIn(WebDriver driver) {
		this.driver=driver;
	}
	public void logIn() {
		WebElement username= driver.findElement(By.className("PhoneInputInput"));
		username.sendKeys("9843334231");
		
		WebElement password= driver.findElement(By.name("password"));
		password.sendKeys("HamroTest123");
		
		WebElement login=driver.findElement(By.className("btn--form"));
		login.click();
	}
}
