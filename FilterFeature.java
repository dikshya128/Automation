import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import org.junit.Assert;

public class FilterFeature {
	private WebDriver driver;
	
	public FilterFeature(WebDriver driver) {
		this.driver=driver;
	}
	
	public void clickCategory() {
		WebElement HBSelect=driver.findElement(By.className("sidebar-category-title-toggle-btn"));
		HBSelect.click();
	}
	
	public void filterPrice() {
		int minimum=1000;
		int maximum=5000;
		clickCategory();
		WebElement price=driver.findElement(By.cssSelector(".inline-form"));
		WebElement minPrice=price.findElement(By.name("priceFrom"));
		minPrice.sendKeys(String.valueOf(minimum));
		
		WebElement maxPrice=price.findElement(By.name("priceTo"));
		maxPrice.sendKeys(String.valueOf(maximum));
		
		WebElement toClick=driver.findElement(By.cssSelector(".form-item.form-item--filterBtn"));
		WebElement filterClick=toClick.findElement(By.className("btn"));
		filterClick.click();
		
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 				 
        List<WebElement> products =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".card-product-linear .card-product-linear-info .priceAndCondition")));

        for (int j = 0; j < products.size(); j++) {
        	WebElement product = products.get(j);
            String productPriceStr = product.findElement(By.className("regularPrice")).getText();

            int productPrice = Integer.parseInt(productPriceStr.replaceAll("[^0-9]", ""));

            Assert.assertTrue("Filtered product price should be between the provided range",
                    productPrice >= minimum & productPrice <= maximum);

        }

	}

}
