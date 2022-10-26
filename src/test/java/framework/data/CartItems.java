package framework.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CartItems {
    
    List<Cart> cartItems;
    
    public CartItems() {
        cartItems = new ArrayList<Cart>();
    }

    public Cart getItem(String productName) {
        return getCartItems().stream()
                .filter(item -> item.getProductName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    public void removeCartItem(String productName) {
        getCartItems().removeIf(item -> item.getProductName().equals(productName));
    }

    public void addCartItem(String productName, BigDecimal productQuantity, BigDecimal productPrice) {
        Cart item = getItem(productName);
        if(item == null) {
            item = new Cart(productName, productQuantity, productPrice);
        } else {
            removeCartItem(productName);
            item.setProductQuantity(item.getProductQuantity().add(productQuantity));
        }
        getCartItems().add(item);
    }

    public void removeAllCartItems() {
        getCartItems().clear();
    }

    public BigDecimal getTotalPrice() {

        if(getCartItems().size() == 0) {
            return BigDecimal.ZERO;
        }
        return getCartItems().stream()
            .map(cart -> cart.getProductPrice().multiply(cart.getProductQuantity()))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(2, RoundingMode.HALF_UP);
    }

    public List<Cart> getCartItems() {
        return cartItems;
    }
}
