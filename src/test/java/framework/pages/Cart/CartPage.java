package framework.pages.Cart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import framework.pages.BasePage;
import framework.pages.CheckOut.CheckOutPage;

public class CartPage extends BasePage {

    private static final String CARTPAGE_CONTAINER = "app-cart";
    private static final String CHECKOUT_BUTTON_TEXT = "Check Out";
    private static final String EMPTYCART_BUTTON_TEXT = "Empty Cart";

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getCartPageContainer() {
        return driver.findElement(By.xpath("//" + CARTPAGE_CONTAINER + ""));
    }

    public WebElement getCartTable() {
        return getCartPageContainer().findElement(By.xpath(".//table")); 
    }

    public int getCartTotalRows() {
        List<WebElement> rows = getCartTable().findElements(By.xpath(".//tbody//tr"));
        return rows.size();
    }

    public WebElement findCartRow(String itemName) {
        List<WebElement> rows = getCartTable().findElements(By.xpath(".//tbody//tr"));       
        return rows.stream()
            .filter(element -> 
                itemName.equals(
                    element.findElement(By.xpath(".//td[" + (getColumnIndex(getCartTable(), "Item") + 1) + "]")).getText().trim()))
            .findFirst()
            .orElse(null);                 
    }

    public BigDecimal getToyQuantity(String itemName) {
        WebElement cartElement = findCartRow(itemName)
            .findElement(By.xpath(".//td[" + (getColumnIndex(getCartTable(), "Quantity") + 1) + "]//input"));
        
        BigDecimal quantity = new BigDecimal(cartElement.getAttribute("value"));
        return quantity;
    }

    public BigDecimal getToySubTotal(String itemName) {
        WebElement cartElement = findCartRow(itemName)
            .findElement(By.xpath(".//td[" + (getColumnIndex(getCartTable(), "SubTotal") + 1) + "]"));
        
        BigDecimal price = new BigDecimal(cartElement.getText().replace("$", "").trim());
        return price;
    }

    public BigDecimal getTotalPrice() {
        WebElement totalPrice = getCartTable().findElement(By.className("cdk-column-total"));
        return new BigDecimal(totalPrice.getText().replace("Total ", "").trim())
            .setScale(2, RoundingMode.HALF_UP);
    }
    
    public RemoveItemModal removeProduct(String productName) {

        findCartRow(productName).findElement(By.xpath(".//td[" + (getColumnIndex(getCartTable(), "Actions") + 1) + "]//a")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new RemoveItemModal(driver);
    }

    public EmptyCartModal emptyCartProducts() {

        clickLinkByText(getCartTable(), EMPTYCART_BUTTON_TEXT);        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new EmptyCartModal(driver);        
    }

    public CheckOutPage clickCheckOutButton() {
        clickLinkByText(getCartTable(), CHECKOUT_BUTTON_TEXT);
        return new CheckOutPage(driver);
    }
}
