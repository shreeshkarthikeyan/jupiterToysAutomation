package framework.tests;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import framework.Report;
import framework.data.CartItems;
import framework.data.ContactDetails;
import framework.data.DeliveryDetails;
import framework.data.PaymentDetails;
import framework.execution.BaseTest;
import framework.execution.Browser;
import framework.pages.BasePage;
import framework.pages.HomePage;
import framework.pages.ShopPage;
import framework.pages.Cart.CartPage;
import framework.pages.Cart.EmptyCartModal;
import framework.pages.Cart.RemoveItemModal;
import framework.pages.CheckOut.CheckOutPage;
import framework.pages.CheckOut.ConfirmOrderForm;

public class testCase1 extends BaseTest {

    List<Map<String, BigDecimal>> productList;
    ContactDetails contactDetails;
    DeliveryDetails deliveryDetails;
    PaymentDetails paymentDetails;
    boolean removeCartItem;
    List<String> removeItems;
    boolean emptyCartItem;
    public testCase1() {
        productList = 
            Arrays.asList(
                Map.of("Teddy Bear", BigDecimal.valueOf(2)),
                Map.of("Stuffed Frog",BigDecimal.valueOf(3)),
                Map.of("Handmade Doll", BigDecimal.valueOf(1)),
                Map.of("Teddy Bear", BigDecimal.valueOf(1))                                    
            );

        contactDetails = new ContactDetails();
        contactDetails.setFirstName("Shreesh");
        contactDetails.setLastName("Karthikeyan");
        contactDetails.setEmail("skarthikeyan@planittesting.com");
        contactDetails.setPhoneNumber("0456314971");
        contactDetails.setAddressline1("123, Bond St");
        contactDetails.setSuburb("Auburn");
        contactDetails.setState("VIC");
        contactDetails.setPostcode("3123");

        deliveryDetails = new DeliveryDetails();
        deliveryDetails.setSameAsContactDetails(true);

        paymentDetails = new PaymentDetails();
        paymentDetails.setCardNumber("1234567812345678");
        paymentDetails.setCardType("Mastercard");
        paymentDetails.setNameOnCard("Shreesh Karthikeyan");
        paymentDetails.setExpiryDate("11/04");
        paymentDetails.setCvv("123");
        removeCartItem = true;
        removeItems = Arrays.asList("Teddy Bear");
        emptyCartItem = false;
    }
    
    @Test
    public void tc1() {

        BasePage basePage = new BasePage(Browser.getInstance());
        HomePage homePage = basePage.clickHomeButton();

        ShopPage shopPage = homePage.clickStartShoppingButton();      
        CartItems cartItems = new CartItems();
        
        productList.stream().forEach(m -> m.entrySet().stream().forEach(e -> {
            String productName = e.getKey();
            BigDecimal productQty = e.getValue();
            shopPage.addProductToCart(productName, productQty);
            cartItems.addCartItem(productName, productQty, shopPage.getProductPrice(productName));
        }));

        CartPage cartPage = basePage.clickCartButton();

        if(!checkCartDetails(cartPage, cartItems)) {
            return;
        }

        //Check for total price
        if(cartPage.getTotalPrice().compareTo(cartItems.getTotalPrice()) != 0) {
            Report.logFail("Mismatch in Cart Total price");
            return;
        }

        if(removeCartItem) {
            removeItems.stream().forEach(toyName -> {
                RemoveItemModal removeItemModal = cartPage.removeProduct(toyName);
                removeItemModal.clickConfirmButton();
                cartItems.removeCartItem(toyName);
            });

            if(!checkCartDetails(cartPage, cartItems)) {
                return;
            }
        }

        if(emptyCartItem) {
            EmptyCartModal emptyCartModal = cartPage.emptyCartProducts();
            emptyCartModal.clickConfirmButton();
            cartItems.removeAllCartItems();
        }

        CheckOutPage checkOutPage = cartPage.clickCheckOutButton();
        
        ConfirmOrderForm confirmOrderForm = checkOutPage.fillInAllForms(contactDetails, deliveryDetails, paymentDetails);
        confirmOrderForm.clickCollapseAll();
        if(!checkOrderDetails(confirmOrderForm, cartItems)) {
           return; 
        }
        if(!checkDeliveryAndContactDetails(confirmOrderForm)) {
            return;
        }
        if(!checkPaymentDetails(confirmOrderForm)) {
            return;
        }
        confirmOrderForm.clickSubmitOrderButton();

        Report.logStep("Payment Status: " + checkOutPage.getPaymentStatus());
        Report.logStep("Order Id: " + checkOutPage.getOrderId());
    }

    public boolean checkCartDetails(CartPage cartPage, CartItems cartItems) {

        boolean quantityResult = 
            cartItems.getCartItems().stream()
                .allMatch(cart -> cart.getProductQuantity()
                    .compareTo(cartPage.getToyQuantity(cart.getProductName())) == 0);

        if(quantityResult) {
            boolean subTotalResult = 
                cartItems.getCartItems().stream()
                    .allMatch(cart -> cart.getProductQuantity().multiply(cart.getProductPrice())
                        .compareTo(cartPage.getToySubTotal(cart.getProductName())) == 0);
            
            if(subTotalResult) {
                return true;
            }
            Report.logFail("Issue in Cart Item's Sub Total");
            return false;
        }
        Report.logFail("Issue in Cart Item's Quantity");
        return false;

    }


    public boolean checkOrderDetails(ConfirmOrderForm confirmOrderForm, CartItems cartItems) {

        boolean quantityResult = 
            cartItems.getCartItems().stream()
                .allMatch(cart -> cart.getProductQuantity()
                    .compareTo(confirmOrderForm.getToyQuantity("Order Details", cart.getProductName())) == 0);

        if(quantityResult) {
            boolean subTotalResult = 
                cartItems.getCartItems().stream()
                    .allMatch(cart -> cart.getProductQuantity().multiply(cart.getProductPrice())
                        .compareTo(confirmOrderForm.getToySubTotal("Order Details", cart.getProductName())) == 0);
            
            if(subTotalResult) {
                confirmOrderForm.clickCollapseAll();
                return true;
            }
            Report.logFail("Issue in Cart Item's Sub Total");
            return false;
        }
        Report.logFail("Issue in Cart Item's Quantity");
        return false;

    }

    public boolean checkDeliveryAndContactDetails(ConfirmOrderForm confirmOrderForm) {

        if(checkContactDetails(confirmOrderForm)) {
            if(checkDeliveryDetails(confirmOrderForm)) {
                confirmOrderForm.clickCollapseAll();
                return true;
            }
            return false;
        }
        return false;
        
    }

    public boolean checkContactDetails(ConfirmOrderForm confirmOrderForm) {
        //Contact Details
        String contact_name = contactDetails.getFirstName() + " " + contactDetails.getLastName();
        String contact_address = contactDetails.getAddressline1() + ",\n"
                                 + (contactDetails.getAddressline2() == null ? ",\n" : contactDetails.getAddressline2() + ",\n")
                                 + contactDetails.getSuburb() + ",\n" 
                                 + contactDetails.getState() + " " + contactDetails.getPostcode();

        if(confirmOrderForm.getContactDetailsName().equals(contact_name)) {
            if(confirmOrderForm.getContactDetailsAddress().equals(contact_address)) {
                if(confirmOrderForm.getDetails("Delivery & Contact Details", "Email").equals(contactDetails.getEmail())) {
                    if(confirmOrderForm.getDetails("Delivery & Contact Details", "Phone").equals(contactDetails.getPhoneNumber())) {
                        return true;
                    }
                    Report.logFail("Mismatch in Phone Number");
                    return false;
                }
                Report.logFail("Mismatch in Email Address");
                return false;
            }
            Report.logFail("Mismatch in Contact Address");
            return false;
        }
        Report.logFail("Mismatch in Contact Name");
        return false;
    }

    public boolean checkDeliveryDetails(ConfirmOrderForm confirmOrderForm) {

        String delivery_name;
        String delivery_address;
        
        if(deliveryDetails.isSameAsContactDetails()) 
        {
            delivery_name = contactDetails.getFirstName() + " " + contactDetails.getLastName();
            delivery_address = contactDetails.getAddressline1() + ",\n"
                                    + (contactDetails.getAddressline2() == null ? ",\n" : contactDetails.getAddressline2() + ",\n")
                                    + contactDetails.getSuburb() + ",\n" 
                                    + contactDetails.getState() + " " + contactDetails.getPostcode();
        } else {
            delivery_name = deliveryDetails.getName();
            delivery_address = deliveryDetails.getAddressline1() + ",\n"
                                    + (deliveryDetails.getAddressline2() == null ? ",\n" : deliveryDetails.getAddressline2() + ",\n")
                                    + deliveryDetails.getSuburb() + ",\n" 
                                    + deliveryDetails.getState() + " " + deliveryDetails.getPostcode();
        }

        if(confirmOrderForm.getContactDetailsName().equals(delivery_name)) {
            if(confirmOrderForm.getContactDetailsAddress().equals(delivery_address)) {
                return true;
            }
            Report.logFail("Mismatch in Delivery Address");
            return false;
        }
        Report.logFail("Mismatch in Delivery Name");
        return false;
    }

    public boolean checkPaymentDetails(ConfirmOrderForm confirmOrderForm) {

        if(confirmOrderForm.getDetails("Payment Details", "Card Name").equals(paymentDetails.getNameOnCard())) {
            if(confirmOrderForm.getDetails("Payment Details", "Card Number").equals(paymentDetails.getCardNumber())) {
                if(confirmOrderForm.getDetails("Payment Details", "Card Type").equals(paymentDetails.getCardType())) {
                    if(confirmOrderForm.getDetails("Payment Details", "Card Expiry").equals(paymentDetails.getExpiryDate())) {
                        if(confirmOrderForm.getDetails("Payment Details", "Card CVV").equals(paymentDetails.getCvv())) {
                            confirmOrderForm.clickCollapseAll();
                            return true;
                        }
                        Report.logFail("Mismatch in Card CVV");
                        return false;
                    }
                    Report.logFail("Mismatch in Card Expiry");
                    return false;
                }
                Report.logFail("Mismatch in Card Type");
                return false;
            }
            Report.logFail("Mismatch in Card Number");
            return false;
        }
        Report.logFail("Mismatch in Card Name");
        return false;
    }
}
