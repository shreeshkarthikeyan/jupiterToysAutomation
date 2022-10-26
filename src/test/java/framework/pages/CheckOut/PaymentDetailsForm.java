package framework.pages.CheckOut;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import framework.data.PaymentDetails;

public class PaymentDetailsForm extends CheckOutPage {

    private static final String CARD_NUMBER_TEXT = "Card Number";
    private static final String CARD_TYPE_TEXT = "Card Type";
    private static final String NAME_ON_CARD_TEXT = "Name on Card";
    private static final String EXPIRY_DATE_TEXT = "Expiry Date";
    private static final String CVV_TEXT = "CVV";
    private static final String NEXT_PAGE_TEXT = "Next";

    public PaymentDetailsForm(WebDriver driver) {
        super(driver);
    }

    public ConfirmOrderForm fillInPaymentDetails(PaymentDetails paymentDetails) {

        if(!checkForActiveTab().equals("Payment Details")) {
            WebElement headerContainer = getCheckOutPageContainer().findElement(By.xpath(".//*[contains(@class,\"header-container\")]"));
            List<WebElement> headers = headerContainer.findElements(By.xpath(".//mat-step-header"));
        
            headers.stream()
                .filter(element -> element.getAttribute("ng-reflect-label").equals("Payment Details"))
                .findFirst()
                .orElse(null)
                .click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        setTextInTextField(getActiveFormContainer(), CARD_NUMBER_TEXT, paymentDetails.getCardNumber());
        selectTextInOverlay(getActiveFormContainer(), CARD_TYPE_TEXT, paymentDetails.getCardType());
        setTextInTextField(getActiveFormContainer(), NAME_ON_CARD_TEXT, paymentDetails.getNameOnCard());
        setTextInTextField(getActiveFormContainer(), EXPIRY_DATE_TEXT, paymentDetails.getExpiryDate());
        setTextInTextField(getActiveFormContainer(), CVV_TEXT, paymentDetails.getCvv());
        clickButtonByText(getActiveFormContainer(), NEXT_PAGE_TEXT);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ConfirmOrderForm(driver);
    }
    
}
