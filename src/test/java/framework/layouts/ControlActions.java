package framework.layouts;

import java.util.List;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ControlActions {
    
    protected WebDriver driver;
    
    public ControlActions(WebDriver driver) {
        this.driver = driver;
    }

    public void clickButtonByTitle(String title) {
        driver.findElement(By.xpath(".//button[@title=\"" + title + "\"]")).click();
    }

    public void clickButtonByText(WebElement container, String buttonText) {
        try {
            container.findElement(By.xpath(".//button[.//*[text()=\"" + buttonText + "\"]]")).click();
        }catch(Exception e) {
            container.findElement(By.xpath(".//button[.//*[text()=\"" + buttonText + "\"]]")).click();
        }
    }

    public void clickLinkByText(WebElement container, String linkText) {
        try {
            container.findElement(By.xpath(".//a[contains(text(),\"" + linkText + "\") or text()=\"" + linkText + "\"]")).click();
        }catch(Exception e) {
            container.findElement(By.xpath(".//a[contains(text(),\"" + linkText + "\") or text()=\"" + linkText + "\"]")).click();
        }
    }

    public void setTextInTextField(WebElement container, String label, String value) {        
        TextField lightningField = new TextField(driver, container, label);
        lightningField.setValue(value);
    }

    public void selectTextInOverlay(WebElement container, String label, String value) {        
        SelectInOverlay lightningField = new SelectInOverlay(driver, container, label);
        lightningField.selectValue(value);
    }

    public int getColumnIndex(WebElement tableContainer, String columnHeader) {
        List<WebElement> headers = tableContainer.findElements(By.xpath(".//thead//th"));
        return IntStream.range(0, headers.size())
            .filter(i -> 
                columnHeader.equals(headers.get(i).getText().trim()))
            .findFirst()
            .orElse(-1);
    }
}
