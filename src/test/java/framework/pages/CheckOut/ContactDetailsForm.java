package framework.pages.CheckOut;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import framework.data.ContactDetails;

public class ContactDetailsForm extends CheckOutPage {

    private static final String FIRST_NAME_TEXT = "First Name";
    private static final String LAST_NAME_TEXT = "Last Name";
    private static final String EMAIL_TEXT = "Email";
    private static final String PHONE_NUMBER_TEXT = "Phone Number";
    private static final String ADDRESS_LINE1_TEXT = "Address Line 1";
    private static final String SUBURB_TEXT = "Suburb";
    private static final String STATE_TEXT = "State";
    private static final String POSTCODE_TEXT = "Postcode";
    private static final String NEXT_PAGE_TEXT = "Next";

    public ContactDetailsForm(WebDriver driver) {
        super(driver);
    }

    public DeliveryDetailsForm fillInContactDetails(ContactDetails contactDetails) {

        if(!checkForActiveTab().equals("Contact Details")) {
            WebElement headerContainer = getCheckOutPageContainer().findElement(By.xpath(".//*[contains(@class,\"header-container\")]"));
            List<WebElement> headers = headerContainer.findElements(By.xpath(".//mat-step-header"));
        
            headers.stream()
                .filter(element -> element.getAttribute("ng-reflect-label").equals("Contact Details"))
                .findFirst()
                .orElse(null)
                .click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        setTextInTextField(getActiveFormContainer(), FIRST_NAME_TEXT, contactDetails.getFirstName());
        setTextInTextField(getActiveFormContainer(), LAST_NAME_TEXT, contactDetails.getLastName());
        setTextInTextField(getActiveFormContainer(), EMAIL_TEXT, contactDetails.getEmail());
        setTextInTextField(getActiveFormContainer(), PHONE_NUMBER_TEXT, contactDetails.getPhoneNumber());
        setTextInTextField(getActiveFormContainer(), ADDRESS_LINE1_TEXT, contactDetails.getAddressline1());
        setTextInTextField(getActiveFormContainer(), SUBURB_TEXT, contactDetails.getSuburb());
        selectTextInOverlay(getActiveFormContainer(), STATE_TEXT, contactDetails.getState());
        setTextInTextField(getActiveFormContainer(), POSTCODE_TEXT, contactDetails.getPostcode());
        clickButtonByText(getActiveFormContainer(), NEXT_PAGE_TEXT);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new DeliveryDetailsForm(driver);
    }
    
}
