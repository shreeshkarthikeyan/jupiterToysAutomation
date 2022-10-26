package framework.layouts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Field {
    
    protected WebElement field;
    protected WebDriver driver;

    public Field(WebDriver driver, WebElement container, String label) {
        this(container, label);
        this.driver = driver;        
    }

    public Field(WebElement container, String label) {
        this.field = container.findElement(By.xpath(".//mat-form-field[.//*[text()=\"" + label + "\" or contains(text(),\"" + label + "\")]]"));        
    }
}
