package framework.pages.CheckOut;

import java.math.BigDecimal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfirmOrderForm extends CheckOutPage {

    private static final String COLLAPSE_ALL_BUTTON_TEXT = "Collapse All";
    private static final String SUBMIT_ORDER_BUTTON_TEXT = "Submit Order";

    public ConfirmOrderForm(WebDriver driver) {
        super(driver);
    }

    public WebElement getActiveTabContainer(String tabName) {
        return getActiveFormContainer()
        .findElement(By.xpath(".//mat-expansion-panel[.//*[contains(text(), \"" + tabName + "\")]]"));
    }

    public void clickCollapseAll() {
        clickButtonByText(getActiveFormContainer(), COLLAPSE_ALL_BUTTON_TEXT);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickSubmitOrderButton() {
        clickButtonByText(getActiveFormContainer(), SUBMIT_ORDER_BUTTON_TEXT);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void expandSection(String sectionName) {
        
        getActiveTabContainer(sectionName)
            .findElement(By.xpath(".//*[contains(@class,\"expansion-indicator\")]"))
            .click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebElement getActiveTableContainer(String tabName) {
        if(!getActiveTabContainer(tabName).findElement(By.xpath(".//mat-expansion-panel-header"))
            .getAttribute("class").contains("expanded")) {
                expandSection(tabName);
        }
        WebElement tableContainer = getActiveTabContainer(tabName).findElement(By.xpath(".//table"));
        return tableContainer;
    }

    public WebElement findCartItemRow(String tabName, String itemName) {             
        return getActiveTableContainer(tabName).findElements(By.xpath(".//tbody//tr"))
            .stream()
                .filter(element -> 
                    itemName.equals(
                        element.findElement(By.xpath(".//td[" + (getColumnIndex(getActiveTableContainer(tabName), "Item") + 1) + "]")).getText().trim()))
                .findFirst()
                .orElse(null);                 
    }

    public BigDecimal getToyQuantity(String tabName, String itemName) {
        String quantity = 
            findCartItemRow(tabName, itemName)
                .findElement(By.xpath(".//td[" + (getColumnIndex(getActiveTableContainer(tabName), "Quantity") + 1) + "]")).getText().trim();
        
        return new BigDecimal(quantity);
                    
    }

    public BigDecimal getToySubTotal(String tabName, String itemName) {
        
        String subTotal = 
            findCartItemRow(tabName, itemName)
                .findElement(By.xpath(".//td[" + (getColumnIndex(getActiveTableContainer(tabName), "Sub total") + 1) + "]")).getText()
                .replace("$", "")
                .trim();
        
        return new BigDecimal(subTotal);                    
    }

    public String getDetails(String tabName, String fieldName) {
        return getActiveTableContainer(tabName)
            .findElement(By.xpath(".//tr//td[text()=\"" + fieldName + ": \"]/following-sibling::td"))
            .getText().trim();
    }

    public String getContactDetailsName() {
        return getActiveTableContainer("Delivery & Contact Details")
            .findElement(By.xpath(".//tr//td[text()=\"Name: \"][2]/following-sibling::td"))
            .getText().trim();
    }

    public String getContactDetailsAddress() {
        return getActiveTableContainer("Delivery & Contact Details")
            .findElement(By.xpath(".//tr//td[text()=\"Address: \"][2]/following-sibling::td"))
            .getText().trim();
    }

    
}
