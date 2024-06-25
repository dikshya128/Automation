import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class SearchFunction {
	private WebDriver driver;
	private String csvFile ="./src/searchWishlist.csv";
	private String resultFile="./src/searchResponse.csv";
	
	public SearchFunction(WebDriver driver) {
		this.driver=driver;
	}
	
	public void search() throws CsvValidationException, IOException {
		WebElement searchBox=driver.findElement(By.name("searchValue"));
		CSVReader reader=new CSVReader(new FileReader(csvFile));
        CSVWriter writer = new CSVWriter(new FileWriter(resultFile));
        
        String[] headings = {"Search Term", "Product Title", "Product Price", "Product Description"};
        writer.writeNext(headings);

		String[] cell;
		searchBox.clear();
		while((cell=reader.readNext())!=null) {
			for(int i=0;i<cell.length;i++) {
				String name=cell[i];
				searchBox.clear();
				searchBox.sendKeys(name);
				searchBox.submit();
                
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 				 
	            List<WebElement> products =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".home--listings .card-product-linear")));
	            
	                for (int j = 0; j < 4 && j < products.size(); j++) {
	                WebElement product = products.get(j);

	                String title = getProductTitle(product);
                    String price = getProductPrice(product);
                    String description = getProductDescription(product);
	                
	                String[] data = {name, title, price, description};
                    writer.writeNext(data);  
	           }
			}

		reader.close();
		writer.close();
		}
	}

	private String getProductTitle(WebElement product) {
        return product.findElement(By.className("product-title")).getText();
    }

    private String getProductPrice(WebElement product) {
        return product.findElement(By.className("regularPrice")).getText().replace("रू", "Rs");
    }

    private String getProductDescription(WebElement product) {
        return product.findElement(By.className("description")).getText();
    }
    
}
