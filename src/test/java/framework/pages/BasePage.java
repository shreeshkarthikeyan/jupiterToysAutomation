package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import framework.layouts.BaseModal;
import framework.layouts.ControlActions;
import framework.pages.Cart.CartPage;

public class BasePage extends ControlActions {
    
    private static final String HOME_BUTTON_TEXT = "Home";
    private static final String SHOP_BUTTON_TEXT = "Shop";
    private static final String CART_BUTTON_TEXT = "Cart";
    private static final String CONTACT_BUTTON_TEXT = "Contact";
    private static final String LOGIN_BUTTON_TEXT = "Login";   
    private static final String MENU_ICON_XPATH = "mat-icon-button";
    private static final String TOOLBAR_CONTAINER = "mat-toolbar.mat-primary.mat-toolbar-single-row";
    public BasePage(WebDriver driver) {
        super(driver);
    }

    public WebElement getToolbarContainer() {
        return driver.findElement(By.cssSelector(TOOLBAR_CONTAINER));  
    }

    public HomePage clickHomeButton() {
        clickButtonByText(getToolbarContainer(), HOME_BUTTON_TEXT);
        return new HomePage(driver);      
    }

    public ShopPage clickShopButton() {
        clickButtonByText(getToolbarContainer(), SHOP_BUTTON_TEXT);
        return new ShopPage(driver);       
    }

    public CartPage clickCartButton() {
        clickButtonByText(getToolbarContainer(), CART_BUTTON_TEXT);
        return new CartPage(driver);
    }

    public void clickContactButton() {
        getToolbarContainer().findElement(By.xpath(".//*[contains(@class,\"" + MENU_ICON_XPATH + "\")]")).click();
        WebElement overlayContainer = new BaseModal(driver).getModalContainer();
        clickButtonByText(overlayContainer, CONTACT_BUTTON_TEXT);
    }

    public void clickLoginButton() {
        getToolbarContainer().findElement(By.xpath(".//*[contains(@class,\"" + MENU_ICON_XPATH + "\")]")).click();
        WebElement overlayContainer = new BaseModal(driver).getModalContainer();
        clickButtonByText(overlayContainer, LOGIN_BUTTON_TEXT);
    }
}
