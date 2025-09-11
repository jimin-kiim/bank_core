package org.example.domain;

public class BankAccount {
    private int bankAccountNumber;
    private String alias;
    int balance;

    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getAlias() {
        return alias;
    }

    public int getBalance() {
        return balance;
    }

    public void increaseBalance(int depositAmount) {
        this.balance += depositAmount;
    }

    public void decreaseBalance(int withdrawalAmount) {
        this.balance -= withdrawalAmount;
    }
}
