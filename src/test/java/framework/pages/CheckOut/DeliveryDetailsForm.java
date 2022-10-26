package framework.pages.CheckOut;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import framework.data.DeliveryDetails;

public class DeliveryDetailsForm extends CheckOutPage {

    private static final String NAME_TEXT = "Name";
    private static final String ADDRESS_LINE1_TEXT = "Address Line 1";
    private static final String SUBURB_TEXT = "Suburb";
    private static final String STATE_TEXT = "State";
    private static final String POSTCODE_TEXT = "Postcode";
    private static final String NEXT_PAGE_TEXT = "Next";

    public DeliveryDetailsForm(WebDriver driver) {
        super(driver);
    }

    public PaymentDetailsForm fillInDeliverytDetails(DeliveryDetails deliveryDetails) {

        if(!checkForActiveTab().equals("Delivery Details")) {
            WebElement headerContainer = getCheckOutPageContainer().findElement(By.xpath(".//*[contains(@class,\"header-container\")]"));
            List<WebElement> headers = headerContainer.findElements(By.xpath(".//mat-step-header"));
        
            headers.stream()
                .filter(element -> element.getAttribute("ng-reflect-label").equals("Delivery Details"))
                .findFirst()
                .orElse(null)
                .click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(deliveryDetails.isSameAsContactDetails()) {
            if(!chechForSameAsContactDetails()) {
                WebElement element = getActiveFormContainer().findElement(By.xpath(".//mat-radio-button[@ng-reflect-value=\"Yes\"]"));
                element.findElement(By.xpath(".//*[@class=\"mat-radio-container\"]")).click();
            }
        } else {
            //Check which radio button is selected
            if(chechForSameAsContactDetails()) {
                WebElement element = getActiveFormContainer().findElement(By.xpath(".//mat-radio-button[@ng-reflect-value=\"No\"]"));
                element.findElement(By.xpath(".//*[@class=\"mat-radio-container\"]")).click();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            setTextInTextField(getActiveFormContainer(), NAME_TEXT, deliveryDetails.getName());
            setTextInTextField(getActiveFormContainer(), ADDRESS_LINE1_TEXT, deliveryDetails.getAddressline1());
            setTextInTextField(getActiveFormContainer(), SUBURB_TEXT, deliveryDetails.getSuburb());
            selectTextInOverlay(getActiveFormContainer(), STATE_TEXT, deliveryDetails.getState());
            setTextInTextField(getActiveFormContainer(), POSTCODE_TEXT, deliveryDetails.getPostcode());
        }
        clickButtonByText(getActiveFormContainer(), NEXT_PAGE_TEXT);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new PaymentDetailsForm(driver);
    }

    private boolean chechForSameAsContactDetails() {
        String value = getActiveFormContainer().findElement(By.xpath(".//mat-radio-button[contains(@class,\"radio-checked\")]")).getAttribute("value");
        return value.equals("Yes") ? true : false;
    }
    
}
