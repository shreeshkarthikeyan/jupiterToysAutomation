package framework.data;

public class DeliveryDetails {
    
    private boolean isSameAsContactDetails;
    private String name;
    private String addressline1;
    private String addressline2;
    private String suburb;
    private String state;
    private String postcode;

    public DeliveryDetails() {
    }

    public DeliveryDetails(boolean isSameAsContactDetails, String name, String addressline1, String addressline2,
            String suburb, String state, String postcode) {
        this.isSameAsContactDetails = isSameAsContactDetails;
        this.name = name;
        this.addressline1 = addressline1;
        this.addressline2 = addressline2;
        this.suburb = suburb;
        this.state = state;
        this.postcode = postcode;
    }

    public boolean isSameAsContactDetails() {
        return isSameAsContactDetails;
    }

    public void setSameAsContactDetails(boolean isSameAsContactDetails) {
        this.isSameAsContactDetails = isSameAsContactDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    
}
