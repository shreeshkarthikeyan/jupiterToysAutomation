package framework.layouts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BaseModal extends ControlActions {

    public BaseModal(WebDriver driver) {
        super(driver);
    }

    public WebElement getModalContainer() {
        try {
            return driver.findElement(By.xpath("//*[contains(@class,\"cdk-overlay-pane\")]"));
        } catch (Exception e) {
            return driver.findElement(By.xpath("//*[contains(@class,\"cdk-overlay-pane\")]"));
        }
    }
}
