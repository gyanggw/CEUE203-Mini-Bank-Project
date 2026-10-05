package model;

public class Customer {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private static long customerCounter = 100;

    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        customerCounter = customerCounter + 1;
        this.customerId = "CUST" + customerCounter;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return name;
    }

    public String getCustomerEmail() {
        return email;
    }

    public String getCustomerMobile() {
        return mobile;
    }
}
