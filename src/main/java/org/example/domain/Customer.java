package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String name;
    private String type;
    private int age;
    private List<BankAccount> bankAccountList;

    public Customer() {
        bankAccountList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void addNewBankAccount(BankAccount bankAccount) {
        bankAccount.setBankAccountNumber(bankAccountList.size() + 1);
        bankAccountList.add((BankAccount) bankAccount);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
        setType();
    }

    private void setType() {
        if (age <= 13) {
            type = "KID";
        } else if (age <= 18) {
            type = "Teenager";
        } else {
            type = "ADULT";
        }
    }

    public void setId(int size) {
        this.id = size;
    }

    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }
}
