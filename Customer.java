class Customer implements Cloneable
{
    private String name;
    private String email;
    private String mobile;
    private final String customerID;
    private Address address;
    private static long customerCounter = 100;

    public static class Address
    {
        private final String line;
        private final String city;
        private final String pincode;

        public Address(String line, String city, String pincode)
        {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine()
        {
            return line;
        }

        public String getCity()
        {
            return city;
        }

        public String getPincode()
        {
            return pincode;
        }

        public Address clone()
        {
            return new Address(line, city, pincode);
        }

        @Override
        public String toString()
        {
            return line + ", " + city + " - " + pincode;
        }
    }

    private static String generateCustomerId()
    {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    public Customer(String name, String email, String mobile)
    {
        this(name, email, mobile, null);
    }

    public Customer(String name, String email, String mobile, Address address)
    {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerID = generateCustomerId();
        this.address = address;
    }

    private Customer(String name, String email, String mobile, Address address, String customerID)
    {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerID = customerID;
        this.address = address;
    }

    public String getCustomerId()
    {
        return this.customerID;
    }

    public String getCustomerName()
    {
        return this.name;
    }

    public String getCustomerEmail()
    {
        return this.email;
    }

    public String getCustomerMobile()
    {
        return this.mobile;
    }

    public Address getAddress()
    {
        return this.address;
    }

    @Override
    public Customer clone()
    {
        return new Customer(this.name, this.email, this.mobile, this.address != null ? this.address.clone() : null, this.customerID);
    }
}