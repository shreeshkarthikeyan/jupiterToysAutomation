package framework.pages.CheckOut;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import framework.Settings;
import framework.data.ContactDetails;
import framework.data.DeliveryDetails;
import framework.data.PaymentDetails;
import framework.pages.BasePage;

public class CheckOutPage extends BasePage {

    private static final String CHECKOUTPAGE_CONTAINER = "app-checkout";
    
    public CheckOutPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getCheckOutPageContainer() {
        return driver.findElement(By.xpath("//" + CHECKOUTPAGE_CONTAINER + ""));
    }

    public String checkForActiveTab() {
        WebElement headerContainer = getCheckOutPageContainer().findElement(By.xpath(".//*[contains(@class,\"header-container\")]"));
        List<WebElement> headers = headerContainer.findElements(By.xpath(".//mat-step-header"));
        
        WebElement activeTabElement = 
            headers.stream()
                .filter(element -> element.getAttribute("ng-reflect-active").equals("true"))
                .findFirst()
                .orElse(null);
        return activeTabElement.getAttribute("ng-reflect-label").trim();
    }

    public WebElement getActiveFormContainer() {
        WebElement contentContainer = getCheckOutPageContainer().findElement(By.xpath(".//*[contains(@class,\"content-container\")]"));
        return contentContainer.findElement(By.xpath(".//div[contains(@aria-expanded,\"true\")]"));
    }

    public ConfirmOrderForm fillInAllForms( ContactDetails contactDetails, 
                                DeliveryDetails deliveryDetails, 
                                PaymentDetails paymentDetails) {

        DeliveryDetailsForm deliveryDetailsForm = 
            new ContactDetailsForm(driver).fillInContactDetails(contactDetails);
        
        PaymentDetailsForm paymentDetailsForm = 
            deliveryDetailsForm.fillInDeliverytDetails(deliveryDetails);

        ConfirmOrderForm confirmOrderForm = 
            paymentDetailsForm.fillInPaymentDetails(paymentDetails);

        return confirmOrderForm;

    }

    public boolean waitForConfirmationMessageToAppear() {
        int timeout = Settings.getTimeout_seconds();
        while(timeout > 0)
        {
            try {
                if(getAlertContainer().isDisplayed()) {
                    System.out.println("Confirmation msg present");
                    break;                    
                }
            } catch(Exception e){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            timeout--;
            System.out.println(timeout);
        }
        return true;
    }

    public WebElement getAlertContainer() {
        return getCheckOutPageContainer().findElement(By.className("alert"));
    }

    public String getPaymentStatus() {
        String paymentStatus = null;
        if(waitForConfirmationMessageToAppear()) {
            paymentStatus = getAlertContainer().findElement(By.xpath(".//strong[1]"))
                .getText().trim();
        }
        return paymentStatus;
    }

    public String getOrderId() {
        String orderId = null;
        if(waitForConfirmationMessageToAppear()) {
            orderId = getAlertContainer().findElement(By.xpath(".//strong[2]"))
                .getText().trim();
        }
        return orderId;
    }
    
}
