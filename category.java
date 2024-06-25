import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVWriter;

public class category {
	private WebDriver driver;
	private String resultFile="./src/categoryDetail.csv";

	
	public category(WebDriver driver) {
		this.driver=driver;
		
	}
    
    public void findCategory() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(resultFile));
        String[] headings= {"Category","Count"};
        writer.writeNext(headings);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 				 
        List<WebElement> sidebarContainers = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".sidebar-category-item.undefined")));        


        for(int j = 0; j<sidebarContainers.size(); j++) {
        	 WebElement container = sidebarContainers.get(j);
        	 WebElement titleElement = container.findElement(By.className("category-name"));
        	 String title=titleElement.getText();
        	 
        	 WebElement countElement=container.findElement(By.className("category-totalNumber"));
        	 String count=countElement.getText().replaceAll("\\(|\\)", "");
        	 
        	 String[] data= {title,count};
        	 writer.writeNext(data);

        }
		writer.close();

    }
    
}
