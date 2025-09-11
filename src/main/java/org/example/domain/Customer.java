package org.example.domain;

import org.example.constants.Type;

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

    public void addNewBankAccount(BankAccount bankAccount) { bankAccountList.add((BankAccount) bankAccount);}

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
        setType();
    }

    private void setType() {
        if (age <= 13) {
            type = Type.KID.getValue();
        } else if (age <= 18) {
            type = Type.TEENAGER.getValue();
        } else {
            type = Type.ADULT.getValue();
        }
    }

    public void setId(int size) {
        this.id = size;
    }

    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }
}
