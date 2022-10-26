package framework.pages;

import java.math.BigDecimal;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import framework.Report;
import framework.Settings;

public class ShopPage extends BasePage {

    private static final String SHOPPAGE_CONTAINER = "app-toy-list";
    
    public ShopPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getHomePageContainer() {
        return driver.findElement(By.xpath("//" + SHOPPAGE_CONTAINER + ""));
    }

    public List<WebElement> getAllProducts() {
        WebElement product = getHomePageContainer().findElement(By.className("products"));
        return product.findElements(By.className("product"));   
    }

    public WebElement getProduct(String productName) {
        WebElement product = 
            getAllProducts().stream()
                .filter(element -> 
                    element.findElement(By.xpath(".//h4"))
                        .getText()
                        .equals(productName))
                .findFirst()
                .orElse(null);
        
        return product;
    }

    public void addProductToCart(String productName, BigDecimal quantityToAdd) {
        try {
            do {
                clickLinkByText(getProduct(productName), "Buy");
                waitForConfirmationMessageToDisapper();
                quantityToAdd = quantityToAdd.subtract(BigDecimal.ONE);
            } while(quantityToAdd.compareTo(BigDecimal.ZERO) > 0);
        } catch(Exception e) {
            Report.logStep(productName + " is not available in the list");
        }
    }

    public boolean waitForConfirmationMessageToDisapper() {
        int timeout = Settings.getTimeout_seconds();
        while(timeout > 0)
        {
            try {
                if(driver.findElement(By.xpath("//div[@class=\"cdk-global-overlay-wrapper\"]")).isDisplayed()) {
                    System.out.println("Confirmation present");
                    Thread.sleep(500);
                }
            } catch(Exception e){
                // Overlay disappeared
                break;
            }
            timeout--;
            System.out.println(timeout);
        }
        return true;
    }


    public BigDecimal getProductPrice(String productName) {

        try {
            String price = getProduct(productName).findElement(By.className("product-price"))
                            .getText()
                            .replace("$", "");
            
            return new BigDecimal(price);
        } catch(Exception e) {
            Report.logStep(productName + " is not available in the list");
            return BigDecimal.ZERO;
        }
    }
}
