package framework.data;

public class PaymentDetails {
    private String cardNumber;
    private String cardType;
    private String NameOnCard;
    private String expiryDate;
    private String cvv;
    
    public PaymentDetails() {
    }

    public PaymentDetails(String cardNumber, String cardType, String nameOnCard, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        NameOnCard = nameOnCard;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getNameOnCard() {
        return NameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        NameOnCard = nameOnCard;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    
}
