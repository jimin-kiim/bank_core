package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static List<Customer> customerList;
    private static List<BankAccount> bankAccountList;

    public Bank() {
        customerList = new ArrayList<>();
        bankAccountList = new ArrayList<>();
    }
    public static List<Customer> getCustomerList() {
        return customerList;
    }

    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    public static List<BankAccount> getBankAccountList() { return bankAccountList;}

    public void addBankAccount(BankAccount bankAccount) {bankAccountList.add(bankAccount);
    }
}
