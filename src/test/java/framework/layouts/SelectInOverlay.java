package framework.layouts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelectInOverlay extends Field {

    private WebElement selectfield;

    public SelectInOverlay(WebDriver driver, WebElement container, String label) {
        super(driver, container, label);
        this.selectfield = this.field.findElement(By.xpath(".//mat-select"));
    }

    public void selectValue(String value) {
        BaseModal baseModal = clickSelectField();
        baseModal.getModalContainer().findElement(By.xpath(".//mat-option[.//*[text()=\"" + value + "\"]]")).click();
    }

    public BaseModal clickSelectField() {
        selectfield.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new BaseModal(driver);        
    }
    
}
