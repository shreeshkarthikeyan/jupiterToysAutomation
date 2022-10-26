package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private static final String HOMEPAGE_CONTAINER = "app-home";
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    public WebElement getHomePageContainer() {
        return driver.findElement(By.xpath("//" + HOMEPAGE_CONTAINER + ""));
    }
    
    public ShopPage clickStartShoppingButton() {
        clickLinkByText(getHomePageContainer(), "Start Shopping");
        return new ShopPage(driver);
    }
}
