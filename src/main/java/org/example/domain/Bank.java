package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static List<Customer> customerList;

    public Bank() {
        customerList = new ArrayList<>();
    }
    public static List<Customer> getCustomerList() {
        return customerList;
    }

    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }
}
