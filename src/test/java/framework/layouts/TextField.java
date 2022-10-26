package framework.layouts;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextField extends Field {

    private WebElement textfield;

    public TextField(WebDriver driver, WebElement container, String label) {
        super(driver, container, label);
        this.textfield = this.field.findElement(By.xpath(".//input"));
    }

    public void setValue(String value) {
        textfield.clear();
        textfield.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        textfield.sendKeys(value);
    }

    public String getValue() {
        return textfield.getAttribute("value");
    }
    
}
