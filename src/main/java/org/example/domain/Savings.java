package org.example.domain;

public class Savings extends BankAccount {
    private String maturityDate;

    public Savings(int bankAccountNumber) {
        super(bankAccountNumber);
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }
}
