import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.opencsv.exceptions.CsvValidationException;

public class Main {
	
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver","./chromedriver-win64/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		
		Map<String, Object> prefs = new	HashMap<>();         
		prefs.put("profile.default_content_setting_values.geolocation", 2);         
		
		options.setExperimentalOption("prefs", prefs);   
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://hamrobazaar.com/login");
		
		LogIn user=new LogIn(driver);
		user.logIn();
		
		timeDelay();
		category findCategory=new category(driver);
		try {
			findCategory.findCategory();
		} catch (IOException e) {
			System.err.println("The file doesnot exist or is replaced!");			
			e.printStackTrace();
		}

		FilterFeature filter=new FilterFeature(driver);
		filter.filterPrice();
		timeDelay();
		
		SearchFunction discover=new SearchFunction(driver);
		try {
			discover.search();
		} catch (CsvValidationException e) {
			System.err.println("The file doesnot validate the CSV file format!");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("The file doesnot exist or is replaced!");
			e.printStackTrace();
		}

		driver.quit();
		
	}
	public static void timeDelay() {
		try {
            Thread.sleep(10000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}

