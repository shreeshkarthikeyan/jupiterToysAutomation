package framework.pages.Cart;

import org.openqa.selenium.WebDriver;

import framework.layouts.BaseModal;

public class EmptyCartModal extends BaseModal {

    private static final String CONFIRM_BUTTON_TEXT = "Yes";
    private static final String REJECT_BUTTON_TEXT = "No";

    public EmptyCartModal(WebDriver driver) {
        super(driver);
    }

    public void clickConfirmButton() {
        clickButtonByText(getModalContainer(), CONFIRM_BUTTON_TEXT);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void clickRejectButton() {
        clickButtonByText(getModalContainer(), REJECT_BUTTON_TEXT);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
